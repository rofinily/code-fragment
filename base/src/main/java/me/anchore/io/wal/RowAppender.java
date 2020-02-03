package me.anchore.io.wal;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RowAppender implements Closeable {
    Meta meta;
    BufferAppender<?>[] bufferAppenders;

    public RowAppender(Meta meta) {
        this.meta = meta;
        BufferRoller bufferRoller = new BufferRoller(Paths.get("./data"), 4 << 10);
        bufferAppenders = IntStream.range(0, meta.getColumnCount()).mapToObj(meta::getType).map(type -> {
            switch (type) {
                case BYTE:
                    return BufferAppender.ofByte(bufferRoller);
                case INT:
                    return BufferAppender.ofInt(bufferRoller);
                case LONG:
                    return BufferAppender.ofLong(bufferRoller);
                case DOUBLE:
                    return BufferAppender.ofDouble(bufferRoller);
                case STRING:
                    return BufferAppender.ofString(bufferRoller);
                default:
                    throw new UnsupportedOperationException();
            }
        }).toArray(BufferAppender[]::new);
    }

    void append(Row row) {
        for (int i = 0; i < bufferAppenders.length; i++) {
            bufferAppenders[i].append(row.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        // 尝试用nio实现write ahead log
        // 滚动式追加byte buffer，50mb/s
//        final byte[] bytes = Files.readAllBytes(Paths.get("./data"));
        new File("./data").delete();
        final Random r = new Random();
        final byte[] bytes = new byte[16];
        final Row[] rows = Stream.generate(() -> {
            r.nextBytes(bytes);
            return new ListRow(
                    (byte) r.nextInt(255),
                    r.nextLong(),
                    new String(bytes),
                    r.nextDouble(),
                    r.nextInt()
            );
        }).limit(100_0000).toArray(Row[]::new);
        final long start = System.currentTimeMillis();
        try (RowAppender rowAppender = new RowAppender(new MetaImpl())) {
            for (Row row : rows) {
                rowAppender.append(row);
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Override
    public void close() throws IOException {
        Arrays.stream(bufferAppenders).forEach(BufferAppender::close);
    }
}

