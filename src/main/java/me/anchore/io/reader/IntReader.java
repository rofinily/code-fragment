package me.anchore.io.reader;

/**
 * @author anchore
 */
public interface IntReader extends PrimitiveReader {
    int get(long pos);
}