package common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class PacketCodec {
    private PacketCodec() {
    }

    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream);
             ObjectOutputStream objectStream = new ObjectOutputStream(gzipStream)) {
            objectStream.writeObject(object);
            objectStream.flush();
        }
        return byteStream.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (GZIPInputStream gzipStream = new GZIPInputStream(new ByteArrayInputStream(bytes));
             ObjectInputStream inputStream = new ObjectInputStream(gzipStream)) {
            return inputStream.readObject();
        }
    }
}


