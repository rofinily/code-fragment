package anc.io.reader;


import anc.io.writer.ByteArrayWriter;

/**
 * @author anchore
 */
public interface ByteArrayReader extends ObjectReader<byte[]> {
    String CONTENT = ByteArrayWriter.CONTENT;
    String POSITION = ByteArrayWriter.POSITION;
    String LENGTH = ByteArrayWriter.LENGTH;

    byte[] NULL_VALUE = ByteArrayWriter.NULL_VALUE;
}