package me.anchore.io.reader;

/**
 * @author anchore
 */
public interface LongReader extends PrimitiveReader {

    long get(long pos);

}
