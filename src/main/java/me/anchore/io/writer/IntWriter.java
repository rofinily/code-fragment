package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface IntWriter extends PrimitiveWriter {
    void put(long pos, int val);
}