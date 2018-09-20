package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface ByteWriter extends PrimitiveWriter {

    void put(long pos, byte val);

}
