package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface IntWriter extends PrimitiveWriter<Integer> {

    void putInt(long pos, int val);
}