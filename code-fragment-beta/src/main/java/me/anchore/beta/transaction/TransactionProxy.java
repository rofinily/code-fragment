package me.anchore.beta.transaction;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class TransactionProxy implements Proxy {
    private MethodInterceptor interceptor;

    public TransactionProxy(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public <T> T proxy(Class<T> proxyee) {
        Enhancer eh = new Enhancer();
        eh.setSuperclass(proxyee);
        eh.setCallback(interceptor);
        return (T) eh.create();
    }
}