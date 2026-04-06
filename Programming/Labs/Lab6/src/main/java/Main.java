import client.ClientMain;
import server.ServerMain;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  java -jar Lab5-1.0-SNAPSHOT.jar server [port]");
            System.out.println("  java -jar Lab5-1.0-SNAPSHOT.jar client [host] [port]");
            return;
        }

        String mode = args[0].trim().toLowerCase();
        switch (mode) {
            case "server" -> {
                String[] serverArgs = new String[Math.max(0, args.length - 1)];
                if (args.length > 1) {
                    System.arraycopy(args, 1, serverArgs, 0, args.length - 1);
                }
                ServerMain.main(serverArgs);
            }
            case "client" -> {
                String[] clientArgs = new String[Math.max(0, args.length - 1)];
                if (args.length > 1) {
                    System.arraycopy(args, 1, clientArgs, 0, args.length - 1);
                }
                ClientMain.main(clientArgs);
            }
            default -> System.out.println("Unknown mode: " + mode + ". Use 'server' or 'client'.");
        }
    }
}