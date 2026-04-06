package client;

import network.CommandPacket;
import network.PacketCodec;
import network.ResponsePacket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NonBlockingTcpClient {

    private static final int MAX_PACKET_BYTES = 10 * 1024 * 1024;

    private final String host;
    private final int port;

    public NonBlockingTcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // Main entry: send a command and get the response
    public ResponsePacket send(CommandPacket commandPacket, int timeoutMillis)
            throws IOException, ClassNotFoundException {

        try (SocketChannel channel = SocketChannel.open();
             Selector selector = Selector.open()) {

            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(host, port));

            // Wait until connection is complete
            waitForReady(channel, selector, SelectionKey.OP_CONNECT, timeoutMillis);

            // Send the command
            writePacket(channel, selector, commandPacket, timeoutMillis);

            // Read the response
            return readResponse(channel, selector, timeoutMillis);
        }
    }

    // Waits until the channel is ready for the operation
    private void waitForReady(SocketChannel channel, Selector selector, int interest, int timeoutMillis)
            throws IOException {
        long deadline = System.currentTimeMillis() + timeoutMillis;

        while (true) {
            if ((interest == SelectionKey.OP_CONNECT && channel.finishConnect())) {
                return; // connected
            }

            long wait = deadline - System.currentTimeMillis();
            if (wait <= 0 || !isReady(selector, wait, interest)) {
                throw new SocketTimeoutException("Operation timed out for interest: " + interest);
            }
        }
    }

    // Checks if selector reports readiness for a given interest
    private boolean isReady(Selector selector, long waitMillis, int interest) throws IOException {
        int ready = selector.select(waitMillis);
        if (ready == 0) return false;

        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) {
            SelectionKey key = iter.next();
            iter.remove();
            if (!key.isValid()) continue;

            if ((interest == SelectionKey.OP_CONNECT && key.isConnectable())
                    || (interest == SelectionKey.OP_WRITE && key.isWritable())
                    || (interest == SelectionKey.OP_READ && key.isReadable())) {
                return true;
            }
        }
        return false;
    }

    // Sends a CommandPacket
    private void writePacket(SocketChannel channel, Selector selector, CommandPacket command, int timeoutMillis)
            throws IOException {

        byte[] payload = PacketCodec.serialize(command);
        ByteBuffer buffer = ByteBuffer.allocate(4 + payload.length);
        buffer.putInt(payload.length).put(payload).flip();

        channel.register(selector, SelectionKey.OP_WRITE);
        long deadline = System.currentTimeMillis() + timeoutMillis;

        while (buffer.hasRemaining()) {
            long wait = deadline - System.currentTimeMillis();
            if (wait <= 0 || !isReady(selector, wait, SelectionKey.OP_WRITE)) {
                throw new SocketTimeoutException("Write timed out.");
            }
            channel.write(buffer);
        }
    }

    // Reads a ResponsePacket from the server
    private ResponsePacket readResponse(SocketChannel channel, Selector selector, int timeoutMillis)
            throws IOException, ClassNotFoundException {

        channel.register(selector, SelectionKey.OP_READ);

        // Read length prefix
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel, selector, lengthBuffer, timeoutMillis);
        int payloadLength = lengthBuffer.flip().getInt();

        if (payloadLength <= 0 || payloadLength > MAX_PACKET_BYTES) {
            throw new IOException("Invalid response length: " + payloadLength);
        }

        // Read payload
        ByteBuffer payloadBuffer = ByteBuffer.allocate(payloadLength);
        readFully(channel, selector, payloadBuffer, timeoutMillis);

        Object decoded = PacketCodec.deserialize(payloadBuffer.array());
        if (!(decoded instanceof ResponsePacket response)) {
            throw new IOException("Unsupported response packet type");
        }
        return response;
    }

    // Reads bytes into buffer until full, non-blocking
    private void readFully(SocketChannel channel, Selector selector, ByteBuffer buffer, int timeoutMillis)
            throws IOException {

        long deadline = System.currentTimeMillis() + timeoutMillis;

        while (buffer.hasRemaining()) {
            long wait = deadline - System.currentTimeMillis();
            if (wait <= 0 || !isReady(selector, wait, SelectionKey.OP_READ)) {
                throw new SocketTimeoutException("Read timed out.");
            }

            int bytesRead = channel.read(buffer);
            if (bytesRead < 0) throw new IOException("Connection closed by server.");
        }
    }
}