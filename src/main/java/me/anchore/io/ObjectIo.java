package me.anchore.io;

import me.anchore.io.reader.ObjectReader;
import me.anchore.io.writer.ObjectWriter;

/**
 * @author anchore
 * @date 2018/7/20
 */
public interface ObjectIo<T> extends ObjectWriter<T>, ObjectReader<T> {
}