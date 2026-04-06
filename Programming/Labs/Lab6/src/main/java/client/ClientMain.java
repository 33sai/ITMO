package client;

import utilities.MusicBandValidator;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = 5000;
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
                if (port <= 0 || port > 65535) {
                    throw new NumberFormatException("out of range");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid port: " + args[1] + ". Use value in range 1..65535.");
                return;
            }
        }

        Scanner scanner = new Scanner(System.in);
        MusicBandValidator validator = new MusicBandValidator(scanner);

        ClientCommandRegistry commandRegistry = new ClientCommandRegistry();
        NonBlockingTcpClient tcpClient = new NonBlockingTcpClient(host, port);

        System.out.println("Client mode. Server: " + host + ":" + port);
        System.out.println("Type 'help' for commands.");

        new ClientConsole(scanner, validator, commandRegistry, tcpClient).run();
        scanner.close();
    }
}

