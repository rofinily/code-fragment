package me.anchore.io.reader;

import me.anchore.io.writer.StringWriter;

/**
 * @author anchore
 */
public interface StringReader extends ObjectReader<String> {

    String DEFAULT_CHARSET = StringWriter.DEFAULT_CHARSET;

}
