package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface ObjectWriter<T> extends Writer {
    void put(long pos, T val);
}