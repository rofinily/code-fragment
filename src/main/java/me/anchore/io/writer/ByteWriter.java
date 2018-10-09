package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface ByteWriter extends PrimitiveWriter<Byte> {

    void putByte(long pos, byte val);
}