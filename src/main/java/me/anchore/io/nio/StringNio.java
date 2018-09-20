package me.anchore.io.nio;

import me.anchore.io.ObjectIo;
import me.anchore.io.reader.StringReader;
import me.anchore.io.util.IoUtil;
import me.anchore.io.writer.StringWriter;

import java.nio.charset.Charset;

/**
 * @author anchore
 * @date 2018/7/20
 */
public class StringNio extends BaseNio implements StringWriter, StringReader, ObjectIo<String> {
    private ObjectIo<byte[]> obj;

    public StringNio(NioConf conf) {
        super(conf);
        obj = new ByteArrayNio(conf);
    }

    @Override
    public void flush() {

    }

    @Override
    public String get(long pos) {
        return new String(obj.get(pos), Charset.forName("utf8"));
    }

    @Override
    public long getLastPosition(long pos) {
        return 0;
    }

    @Override
    public boolean isReadable() {
        return false;
    }

    @Override
    public void put(long pos, String val) {
        obj.put(pos, val.getBytes(Charset.forName("utf8")));
    }

    @Override
    public void release() {
        IoUtil.release(obj);
        obj = null;
    }
}