package anc.io.writer;

/**
 * @author anchore
 */
public interface ByteArrayWriter extends ObjectWriter<byte[]> {
    String CONTENT = "content";
    String POSITION = "position";
    String LENGTH = "length";
    String LAST_POSITION = "last_position";

    byte[] NULL_VALUE = new byte[0];
}