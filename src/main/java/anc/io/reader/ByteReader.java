package anc.io.reader;

/**
 * @author anchore
 */
public interface ByteReader extends PrimitiveReader {

    byte get(long pos);

}
