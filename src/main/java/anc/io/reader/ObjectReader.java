package anc.io.reader;

/**
 * @author anchore
 */
public interface ObjectReader<T> extends Reader {
    T get(long pos);

    long getLastPosition(long pos);
}