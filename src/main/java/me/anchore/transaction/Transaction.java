package me.anchore.transaction;

/**
 * @author anchore
 * @date 2018/8/17
 */
public interface Transaction<T> {
    void begin();

    void commit();

    void rollback();
}