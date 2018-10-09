package me.anchore.io;


import me.anchore.io.reader.Reader;
import me.anchore.io.writer.Writer;

/**
 * @author anchore
 * @date 2018/7/20
 */
public interface Io<T> extends Writer<T>, Reader<T> {
    enum Type {
        /**
         * 读写类型
         */
        READ, WRITE
    }

    enum DataType {
        /**
         * 数据类型
         */
        BYTE, INT, LONG, DOUBLE,
        BYTE_ARRAY, STRING, BITMAP, LONG_ARRAY,
        REALTIME_COLUMN
    }
}