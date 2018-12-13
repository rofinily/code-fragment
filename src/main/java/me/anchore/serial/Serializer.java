package me.anchore.serial;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author anchore
 * @date 2018/12/13
 */
public interface Serializer {

    <T> void serialize(T t, OutputStream out);

    <T> T deserialize(InputStream in);
}