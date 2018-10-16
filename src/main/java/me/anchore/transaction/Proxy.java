package me.anchore.transaction;

/**
 * @author anchore
 * @date 2018/8/19
 */
public interface Proxy {
    <T> T proxy(Class<T> proxyee);
}