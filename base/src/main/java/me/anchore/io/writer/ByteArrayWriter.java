package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface ByteArrayWriter extends Writer<byte[]> {
    String CONTENT = "content";
    String POSITION = "position";
    String LENGTH = "length";

    byte[] NULL_VALUE = new byte[0];
}