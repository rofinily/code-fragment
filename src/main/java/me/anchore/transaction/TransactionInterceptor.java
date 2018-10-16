package me.anchore.transaction;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class TransactionInterceptor implements MethodInterceptor {
    private Transaction tx;

    public TransactionInterceptor(Transaction tx) {
        this.tx = tx;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.isAnnotationPresent(Transactional.class)) {
            return interceptTx(obj, method, args, proxy);
        }
        return proxy.invokeSuper(obj, args);
    }

    private Object interceptTx(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        try {
            tx.begin();

            Object returned = proxy.invokeSuper(obj, args);

            tx.commit();

            return returned;
        } catch (Throwable t) {
            if (interceptThrowable(t, method.getDeclaredAnnotation(Transactional.class))) {
                tx.rollback();
                return null;
            }
            throw t;
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