package me.anchore.io.writer;

import java.nio.charset.Charset;

/**
 * @author anchore
 */
public interface StringWriter extends Writer<String> {

    Charset CHARSET = Charset.forName("utf8");

    String NULL_VALUE = "";
}