package me.anchore.serial.impl;

import me.anchore.log.Loggers;
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
    public <T> void serialize(T t, OutputStream out) {
        Assert.notNull(out);
        Assert.isInstanceOf(Serializable.class, t);

        try (ObjectOutputStream objOut = new ObjectOutputStream(out)) {
            objOut.writeObject(t);
        } catch (IOException e) {
            Loggers.getLogger().error(e);
        }
    }

    @Override
    public <T> T deserialize(InputStream in) {
        Assert.notNull(in);

        try (ObjectInputStream objIn = new ObjectInputStream(in)) {
            return (T) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Loggers.getLogger().error(e);
            return null;
        }
    }
}