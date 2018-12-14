package me.anchore.serial.impl;

import me.anchore.serial.Serializer;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author anchore
 * @date 2018/12/13
 */
public class JsonSerializer implements Serializer {

    @Override
    public <T> void serialize(T t, OutputStream out) {

    }

    @Override
    public <T> T deserialize(InputStream in) {
        return null;
    }
}