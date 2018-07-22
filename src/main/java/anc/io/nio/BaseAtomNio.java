package anc.io.nio;


import anc.io.util.IoUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * @author anchore
 * @date 2018/7/20
 */
abstract class BaseAtomNio extends BaseNio {
    private FileChannel ch;

    ByteBuffer buf;

    private int currentPage = -1;

    BaseAtomNio(NioConf conf) {
        super(conf);
        init();
    }

    private void init() {
        if (conf.isWrite()) {
            new File(conf.getPath()).mkdirs();
        }
    }

    int getPage(long pos) {
        return (int) (pos >> (conf.getPageSize() - getStep()));
    }

    int getOffset(long pos) {
        return (int) ((pos << getStep()) & ((1 << conf.getPageSize()) - 1));
    }

    void initBuf(int page) {
        if (currentPage == page) {
            return;
        }
        releaseBuffer();
        loadBuffer(page);
    }

    private void loadBuffer(int page) {
        try {
            RandomAccessFile file = new RandomAccessFile(String.format("%s/%d", conf.getPath(), page), conf.isWrite() ? "rw" : "r");
            ch = file.getChannel();

            if (conf.isMapped()) {
                buf = ch.map(conf.isWrite() ? MapMode.READ_WRITE : MapMode.READ_ONLY, 0, 1 << conf.getPageSize());
            } else {
                if (buf == null) {
                    buf = ByteBuffer.allocate(1 << conf.getPageSize());
                }
                ch.read(buf);
                if (conf.isWrite()) {
                    ch.position(0);
                }
            }

            currentPage = page;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseBuffer() {
        if (buf == null) {
            return;
        }
        if (conf.isMapped()) {
            IoUtil.release((MappedByteBuffer) buf);
            buf = null;
            return;
        }
        if (conf.isWrite()) {
            buf.flip();
            try {
                ch.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IoUtil.close(ch);
            }
        }
        buf.clear();
    }

    void setBufPosition(int offset) {
        int newPos = offset + (1 << getStep());
        if (newPos > buf.position()) {
            buf.position(newPos);
        }
    }

    abstract int getStep();

    @Override
    public void flush() {
    }

    @Override
    public boolean isReadable() {
        return false;
    }

    @Override
    public void release() {
        releaseBuffer();
    }
}