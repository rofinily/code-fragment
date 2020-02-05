package me.anchore.serial.impl;

import me.anchore.serial.SerializeException;
import me.anchore.serial.Serializer;
import me.anchore.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author anchore
 * @date 2018/12/13
 */
public class JavaSerializer implements Serializer {

    @Override
    public <T> void serialize(T t, OutputStream out) throws SerializeException {
        Assert.notNull(out);
        Assert.isInstanceOf(Serializable.class, t);

        try (ObjectOutputStream objOut = new ObjectOutputStream(out)) {
            objOut.writeObject(t);
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public <T> T deserialize(InputStream in, Class<T> klass) throws SerializeException {
        Assert.notNull(in);

        try (ObjectInputStream objIn = new ObjectInputStream(in)) {
            return klass.cast(objIn.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializeException(e);
        }
    }
}