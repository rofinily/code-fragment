package me.anchore.io.nio;

import me.anchore.io.Io;
import me.anchore.io.util.IoUtil;
import me.anchore.log.Loggers;
import me.anchore.util.Checker;

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
abstract class BaseAtomNio<T> extends BaseNio implements Io<T> {

    ByteBuffer buf;

    private FileChannel ch;

    private int currentPage = -1, currentFilePage = -1;

    private int currentStart = -1;

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
        releaseBuffer(false);
        loadBuffer(page);
    }

    private void loadBuffer(int page) {
        int filePage = getFilePage(page);
        try {
            if (currentFilePage != filePage) {
                IoUtil.close(ch);
                ch = new RandomAccessFile(String.format("%s/%d", conf.getPath(), filePage), conf.isRead() ? "r" : "rw").getChannel();

                currentFilePage = filePage;
            }

            int fileOffset = getFileOffset(page);
            if (conf.isMapped()) {
                buf = ch.map(conf.isRead() ? MapMode.READ_ONLY : MapMode.READ_WRITE, fileOffset, 1 << conf.getPageSize());
            } else {
                if (buf == null) {
                    buf = ByteBuffer.allocateDirect(1 << conf.getPageSize());
                }
                if (conf.isAppend()) {
                    currentStart = (int) (ch.size() - fileOffset);
                } else {
                    currentStart = 0;
                    ch.read(buf, fileOffset);
                }
            }

            currentPage = page;
        } catch (IOException e) {
            Loggers.getLogger().error(e);
        }
    }

    private int getFilePage(int page) {
        return page >> (conf.getFileSize() - conf.getPageSize());
    }

    private int getFileOffset(int page) {
        return (page & ((1 << (conf.getFileSize() - conf.getPageSize())) - 1)) << conf.getPageSize();
    }

    private void releaseBuffer(boolean discardBuf) {
        if (buf == null) {
            return;
        }

        if (conf.isWrite()) {
            if (conf.isMapped()) {
                // mapped写
                ((MappedByteBuffer) buf).force();
            } else {
                // 非mapped写，buf视为内存块
                buf.limit(buf.position());
                buf.position(currentStart);
                try {
                    ch.write(buf, currentStart + getFileOffset(currentPage));
                } catch (IOException e) {
                    Loggers.getLogger().error(e);
                } finally {
                    try {
                        ch.force(false);
                    } catch (IOException ignore) {
                    }
                }
            }
        }

        buf.clear();
        currentPage = -1;
        currentFilePage = -1;
        currentStart = -1;
        if (discardBuf) {
            IoUtil.release(buf);
            buf = null;
        }
    }

    void setBufPosition(int offset) {
        int newPos = offset + (1 << getStep());
        if (newPos > buf.position()) {
            buf.position(newPos);
        }

        if (currentStart > offset) {
            currentStart = offset;
        }
    }

    abstract int getStep();

    @Override
    public boolean isReadable() {
        File f = new File(conf.getPath());
        if (!f.exists()) {
            return false;
        }
        String[] children = f.list();
        return !Checker.isEmpty(children) && new File(f, children[0]).length() > 0;
    }

    @Override
    public void release() {
        releaseBuffer(true);
    }
}