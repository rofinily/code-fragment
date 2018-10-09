package me.anchore.io.reader;

import me.anchore.io.writer.StringWriter;

import java.nio.charset.Charset;

/**
 * @author anchore
 */
public interface StringReader extends Reader<String> {

    Charset CHARSET = StringWriter.CHARSET;
}