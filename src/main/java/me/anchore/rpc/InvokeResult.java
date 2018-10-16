package me.anchore.rpc;

import java.util.Optional;

/**
 * @author anchore
 * @date 2018/9/20
 */
public interface InvokeResult<T> {

    Optional<T> getResult();

    Optional<? extends Exception> getException();
}