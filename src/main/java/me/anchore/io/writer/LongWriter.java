package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface LongWriter extends PrimitiveWriter<Long> {

    void putLong(long pos, long val);
}