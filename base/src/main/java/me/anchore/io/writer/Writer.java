package me.anchore.io.writer;


import me.anchore.io.Releasable;

/**
 * @author anchore
 */
public interface Writer<T> extends Releasable {

    void put(long pos, T val);
}