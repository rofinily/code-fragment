package me.anchore.io.reader;

/**
 * @author anchore
 */
public interface LongReader extends PrimitiveReader<Long> {

    long getLong(long pos);
}