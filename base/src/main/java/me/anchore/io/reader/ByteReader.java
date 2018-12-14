package me.anchore.io.reader;

/**
 * @author anchore
 */
public interface ByteReader extends PrimitiveReader<Byte> {

    byte getByte(long pos);
}