package me.anchore.io.reader;


import me.anchore.io.Releasable;

/**
 * @author anchore
 */
public interface Reader extends Releasable {
    /**
     * 是否可读
     *
     * @return 是否可读
     */
    boolean isReadable();
}