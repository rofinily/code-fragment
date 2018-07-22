package anc.io.nio;

import anc.io.Io;
import anc.io.Io.DataType;
import anc.util.Crasher;

/**
 * @author anchore
 * @date 2018/7/21
 */
public class Nios {
    @SuppressWarnings("unchecked")
    public static <IO extends Io> IO of(NioConf conf, DataType dataType) {
        switch (dataType) {
            case INT:
                return (IO) new IntNio(conf);
            case LONG:
                return (IO) new LongNio(conf);
            case DOUBLE:
                return (IO) new DoubleNio(conf);
            case STRING:
                return (IO) new StringNio(conf);
            case BYTE:
                return (IO) new ByteNio(conf);
            case BYTE_ARRAY:
            case LONG_ARRAY:
            default:
                return Crasher.crash(String.format(
                        "illegal cube build config: %s as %s at %s", conf.isWrite() ? "write" : "read", dataType, conf.getPath()));
        }
    }
}