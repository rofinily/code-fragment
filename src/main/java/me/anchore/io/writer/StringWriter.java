package me.anchore.io.writer;

/**
 * @author anchore
 */
public interface StringWriter extends ObjectWriter<String> {

    String DEFAULT_CHARSET = "UTF-8";

    String NULL_VALUE = "";

}
