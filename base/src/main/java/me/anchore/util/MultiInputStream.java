package me.anchore.util;

import me.anchore.io.util.IoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author anchore
 * @date 2018/12/13
 */
public class MultiInputStream extends InputStream {

    private Iterator<InputStream> insItr;

    private InputStream currentIn;

    public MultiInputStream(Iterable<InputStream> ins) {
        Assert.notNull(ins);

        this.insItr = ins.iterator();
        moveItr();
    }

    private void moveItr() {
        if (insItr.hasNext()) {
            IoUtil.close(currentIn);
            currentIn = insItr.next();
        } else {
            currentIn = null;
        }
    }

    @Override
    public int read() throws IOException {
        if (currentIn == null) {
            return -1;
        }
        int b = currentIn.read();
        if (b != -1) {
            return b;
        }
        moveItr();
        if (currentIn == null) {
            return -1;
        }
        return currentIn.read();
    }

    @Override
    public void close() {
        IoUtil.close(currentIn);

        while (insItr.hasNext()) {
            IoUtil.close(insItr.next());
        }
    }


}