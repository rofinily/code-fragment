package me.anchore.io.reader;

/**
 * @author anchore
 */
public interface DoubleReader extends PrimitiveReader {

    double get(long pos);

}
