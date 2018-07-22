package anc.io;

import anc.io.reader.ObjectReader;
import anc.io.writer.ObjectWriter;

/**
 * @author anchore
 * @date 2018/7/20
 */
public interface ObjectIo<T> extends ObjectWriter<T>, ObjectReader<T> {
}