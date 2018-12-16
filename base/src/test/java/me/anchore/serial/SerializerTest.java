package me.anchore.serial;

import me.anchore.serial.impl.JavaSerializer;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author anchore
 * @date 2018/12/13
 */
public class SerializerTest {

    @Test
    public void testSerialize() throws SerializeException {
        Serializer serializer = new JavaSerializer();
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        Object oldObj = "anchore";
        serializer.serialize(oldObj, bytesOut);
        Object newObj = serializer.deserialize(new ByteArrayInputStream(bytesOut.toByteArray()));

        Assert.assertEquals(oldObj, newObj);
    }
}