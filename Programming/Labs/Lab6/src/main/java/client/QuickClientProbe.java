package client;

import commandsabstraction.CommandRequest;
import network.CommandPacket;
import network.ResponsePacket;

public class QuickClientProbe {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;
        String commandName = args.length > 2 ? args[2] : "info";
        String argument = args.length > 3 ? args[3] : "";

        NonBlockingTcpClient client = new NonBlockingTcpClient(host, port);
        ResponsePacket response = client.send(new CommandPacket(commandName, new CommandRequest(argument)), 4000);

        System.out.println(response.getResult().getMessage());
        if (response.getResult().getData() != null) {
            System.out.println(response.getResult().getData());
        }
    }
}

