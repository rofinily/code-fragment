package me.anchore.io.reader;

/**
 * @author anchore
 */
public interface DoubleReader extends PrimitiveReader<Double> {

    double getDouble(long pos);
}