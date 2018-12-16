package me.anchore.transaction;

/**
 * @author anchore
 * @date 2018/8/17
 */
public interface TransactionManager<T> {

    Transaction getTransaction(T attach);
}