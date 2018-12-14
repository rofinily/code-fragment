package me.anchore.io.reader;


import me.anchore.io.Releasable;

/**
 * @author anchore
 */
public interface Reader<T> extends Releasable {

    T get(long pos);

    /**
     * 是否可读
     *
     * @return 是否可读
     */
    boolean isReadable();
}