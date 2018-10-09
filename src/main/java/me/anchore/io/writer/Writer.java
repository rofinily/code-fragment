package me.anchore.io.writer;


import me.anchore.io.Flushable;
import me.anchore.io.Releasable;

/**
 * @author anchore
 */
public interface Writer<T> extends Releasable, Flushable {

    void put(long pos, T val);
}