package anc.io.writer;

/**
 * @author anchore
 */
public interface DoubleWriter extends PrimitiveWriter {
    void put(long pos, double val);
}
