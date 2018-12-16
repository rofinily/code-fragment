package me.anchore.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class TransactionHandler implements InvocationHandler {

    private Object proxyee;

    private Transaction tx;

    public TransactionHandler(Object proxyee, Transaction tx) {
        this.proxyee = proxyee;
        this.tx = tx;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Transactional.class)) {
            return doTransaction(method, args);
        }
        return method.invoke(proxyee, args);
    }

    private Object doTransaction(Method method, Object[] args) throws Throwable {
        try {
            tx.begin();

            Object returned = method.invoke(proxyee, args);

            tx.commit();

            return returned;
        } catch (Throwable t) {
            if (interceptThrowable(t, method.getDeclaredAnnotation(Transactional.class))) {
                tx.rollback();
            }
            throw t;
        } finally {
            tx.close();
        }
    }

    private boolean interceptThrowable(Throwable t, Transactional txConf) {
        for (Class<? extends Throwable> rollbackEx : txConf.rollbackFor()) {
            if (rollbackEx.isAssignableFrom(t.getClass())) {
                return true;
            }
        }
        return false;
    }
}