package server;

import network.CommandPacket;
import network.PacketCodec;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class RequestReader {
    private static final int MAX_PACKET_BYTES = 10 * 1024 * 1024;

    public CommandPacket read(Socket socket) throws IOException, ClassNotFoundException {
        DataInputStream input = new DataInputStream(socket.getInputStream());
        int length = input.readInt();
        if (length <= 0 || length > MAX_PACKET_BYTES) {
            throw new IOException("Invalid packet length: " + length);
        }

        byte[] payload = new byte[length];
        input.readFully(payload);
        Object decoded = PacketCodec.deserialize(payload);
        if (!(decoded instanceof CommandPacket commandPacket)) {
            throw new IOException("Received unsupported packet type.");
        }

        return commandPacket;
    }
}

