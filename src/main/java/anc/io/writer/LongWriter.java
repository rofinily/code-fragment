package anc.io.writer;

/**
 * @author anchore
 */
public interface LongWriter extends PrimitiveWriter {
    void put(long pos, long val);
}