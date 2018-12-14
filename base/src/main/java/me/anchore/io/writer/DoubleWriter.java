package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface DoubleWriter extends PrimitiveWriter<Double> {

    void putDouble(long pos, double val);
}
