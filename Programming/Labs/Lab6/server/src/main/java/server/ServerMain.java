package server;

import common.commandsabstraction.CommandResult;
import common.network.CommandPacket;
import common.network.ResponsePacket;
import server.utilities.CollectionManager;
import server.utilities.FileManager;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) {
        String filename = System.getenv("MUSIC_BAND_DATA");
        if (filename == null || filename.isBlank()) {
            System.err.println("Error: set MUSIC_BAND_DATA environment variable for the server.");
            return;
        }

        int port = 5000;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
                if (port <= 0 || port > 65535) {
                    throw new NumberFormatException("out of range");
                }
            } catch (NumberFormatException e) {
                LOGGER.severe("Invalid port: " + args[0] + ". Use value in range 1..65535.");
                return;
            }
        }

        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(filename);

        try {
            fileManager.load(collectionManager);
            LOGGER.info("Loaded collection. Size: " + collectionManager.size());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not load collection from file. Starting empty.", e);
        }

        ServerCommandRegistry commandRegistry = new ServerCommandRegistry(collectionManager, filename);
        CommandProcessor commandProcessor = new CommandProcessor(commandRegistry);
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor();
        RequestReader requestReader = new RequestReader();
        ResponseSender responseSender = new ResponseSender();

        boolean running = true;
        BufferedReader serverConsoleReader = new BufferedReader(new InputStreamReader(System.in));

        LOGGER.info("Server started on port " + port + ". Server-only commands: save, exit_server");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(500);

            while (running) {
                try {
                    if (serverConsoleReader.ready()) {
                        String serverCommand = serverConsoleReader.readLine();
                        if (serverCommand != null) {
                            serverCommand = serverCommand.trim();

                            if ("exit_server".equals(serverCommand)) {
                                LOGGER.info("Shutdown received. Saving collection...");

                                CommandResult saveResult = commandProcessor.processServerCommand("save");
                                if (saveResult.isSuccess()) {
                                    LOGGER.info("Collection saved successfully.");
                                } else {
                                    LOGGER.warning("Save failed: " + saveResult.getMessage());
                                }
                                running = false;
                                continue;
                            }

                            if (!serverCommand.isEmpty()) {
                                CommandResult serverResult = commandProcessor.processServerCommand(serverCommand);
                                LOGGER.info("Server command '" + serverCommand + "' result: " + serverResult.getMessage());
                            }
                        }
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Failed to read server console command.", e);
                }


                try (Socket clientSocket = connectionAcceptor.accept(serverSocket)) {
                    LOGGER.info("New connection from " + clientSocket.getRemoteSocketAddress());
                    CommandPacket commandPacket = requestReader.read(clientSocket);
                    LOGGER.info("Request received: " + commandPacket.getCommandName());

                    CommandResult result = commandProcessor.processClientCommand(commandPacket);
                    responseSender.send(clientSocket, new ResponsePacket(result));
                    LOGGER.info("Response sent for command: " + commandPacket.getCommandName());
                } catch (EOFException e) {
                    LOGGER.info("Client disconnected before sending a complete request.");
                } catch (SocketTimeoutException ignored) {
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Failed to process client request.", e);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server failed to start or crashed.", e);
        }

        LOGGER.info("Server has shut down.");
    }
}