package me.anchore.rpc;

import java.lang.reflect.Method;

/**
 * @author anchore
 * @date 2018/9/20
 */
public interface Invocation {
    Method getMethod();

    Object[] getArguments();
}