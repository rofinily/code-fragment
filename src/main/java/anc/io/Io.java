package anc.io;


import anc.io.reader.Reader;
import anc.io.writer.Writer;

/**
 * @author anchore
 * @date 2018/7/20
 */
public interface Io extends Writer, Reader {
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