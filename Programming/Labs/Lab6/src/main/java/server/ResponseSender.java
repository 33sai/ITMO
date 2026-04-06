package server;

import network.PacketCodec;
import network.ResponsePacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ResponseSender {
    public void send(Socket socket, ResponsePacket responsePacket) throws IOException {
        byte[] payload = PacketCodec.serialize(responsePacket);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        output.writeInt(payload.length);
        output.write(payload);
        output.flush();
    }
}

