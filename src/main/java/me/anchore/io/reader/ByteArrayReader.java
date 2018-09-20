package me.anchore.io.reader;


import me.anchore.io.writer.ByteArrayWriter;

/**
 * @author anchore
 */
public interface ByteArrayReader extends ObjectReader<byte[]> {
    String CONTENT = ByteArrayWriter.CONTENT;
    String POSITION = ByteArrayWriter.POSITION;
    String LENGTH = ByteArrayWriter.LENGTH;

    byte[] NULL_VALUE = ByteArrayWriter.NULL_VALUE;
}