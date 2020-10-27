package lab6.socket.common;

import java.io.*;
import java.util.Base64;
import java.util.Optional;

public final class SerializableHelper {

    public SerializableHelper() {}

    public static Optional<String> convertToString(final Serializable object) {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return Optional.of(Base64.getEncoder().encodeToString(baos.toByteArray()));
        } catch (final IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static <T extends Serializable> Optional<T> convertFrom(final String objectAsString) {
        final byte[] data = Base64.getDecoder().decode(objectAsString);
        try (final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return Optional.of((T) ois.readObject());
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}