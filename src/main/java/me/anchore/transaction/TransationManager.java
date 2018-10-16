package me.anchore.transaction;

/**
 * @author anchore
 * @date 2018/8/17
 */
public interface TransationManager<T> {
    Transaction<T> getTransaction(T old);
}