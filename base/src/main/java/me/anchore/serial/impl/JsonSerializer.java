package me.anchore.serial.impl;

import me.anchore.serial.SerializeException;
import me.anchore.serial.Serializer;
import me.anchore.util.json.JsonException;
import me.anchore.util.json.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;

/**
 * @author anchore
 * @date 2018/12/13
 */
public class JsonSerializer implements Serializer {

    @Override
    public <T> void serialize(T t, OutputStream out) throws SerializeException {
        try (OutputStreamWriter writer = new OutputStreamWriter(out)) {
            String json = JsonUtil.stringify(t);
            writer.write(json);
        } catch (IOException | JsonException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public <T> T deserialize(InputStream in, Class<T> klass) throws SerializeException {
        try (InputStreamReader reader = new InputStreamReader(in)) {
            CharBuffer charBuffer = CharBuffer.allocate(in.available());
            reader.read(charBuffer);
            return JsonUtil.parse(charBuffer.toString(), klass);
        } catch (IOException | JsonException e) {
            throw new SerializeException(e);
        }
    }
}