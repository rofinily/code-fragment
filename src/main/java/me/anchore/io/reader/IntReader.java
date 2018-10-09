package me.anchore.io.reader;

/**
 * @author anchore
 */
public interface IntReader extends PrimitiveReader<Integer> {

    int getInt(long pos);
}