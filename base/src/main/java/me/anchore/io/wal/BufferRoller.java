package me.anchore.io.wal;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

class BufferRoller implements Closeable {
    ByteBuffer buf;
    FileChannel fc;
    long pos;
    long size;

    BufferRoller(Path path, long size) {
        try {
            fc = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
            this.size = size;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ByteBuffer getCurrentBuffer() {
        return buf;
    }

    ByteBuffer rollToNextBuffer() {
        try {
//            buf = fc.map(MapMode.READ_WRITE, pos, size);
            if (buf != null) {
                buf.flip();
                fc.write(buf, pos);
                pos += size;
                buf.clear();
            } else {
                buf = ByteBuffer.allocateDirect((int) size);
            }
            return buf;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (buf != null) {
            buf.flip();
            fc.write(buf, pos);
            buf = null;
            pos += size;
        }
        fc.close();
    }
}
