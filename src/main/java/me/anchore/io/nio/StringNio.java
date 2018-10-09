package me.anchore.io.nio;

import me.anchore.io.Io;
import me.anchore.io.reader.StringReader;
import me.anchore.io.util.IoUtil;
import me.anchore.io.writer.StringWriter;

/**
 * @author anchore
 * @date 2018/7/20
 */
public class StringNio extends BaseNio implements Io<String> {
    private Io<byte[]> obj;

    public StringNio(NioConf conf) {
        super(conf);
        obj = new ByteArrayNio(conf);
    }

    @Override
    public String get(long pos) {
        return new String(obj.get(pos), StringReader.CHARSET);
    }

    @Override
    public boolean isReadable() {
        return false;
    }

    @Override
    public void put(long pos, String val) {
        obj.put(pos, val.getBytes(StringWriter.CHARSET));
    }

    @Override
    public void release() {
        IoUtil.release(obj);
        obj = null;
    }
}