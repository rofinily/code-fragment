package me.anchore.transaction;

import java.lang.reflect.InvocationHandler;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class TransactionProxy implements Proxy {

    private InvocationHandler handler;

    public TransactionProxy(InvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public <T> T proxy(Class<T> proxyee) {
        Object proxy = java.lang.reflect.Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{proxyee}, handler);
        return proxyee.cast(proxy);
    }
}