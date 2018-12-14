package me.anchore.rpc;

/**
 * @author anchore
 * @date 2018/9/20
 */
public interface Invoker {

    InvokeResult invoke(Invocation invocation) throws InvokeException;
}