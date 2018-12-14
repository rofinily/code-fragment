package me.anchore.snippet.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anchore
 * @date 2018/7/15
 */
public class JdkProxy {
    /**
     * jdk代理增强对象接口行为
     */
    private static void proxy() {
        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{List.class}, new InvocationHandler() {
                    List<Object> proxyee = new ArrayList<>();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object returned = method.invoke(proxyee, args);
                        if (method.equals(List.class.getDeclaredMethod("add", Object.class))) {
                            System.out.println("current size: " + proxyee.size());
                            return returned;
                        }
                        return returned;
                    }
                });

        objects.add(new Object());
        objects.add(new Object());
        objects.clear();
        objects.add(new Object());
    }

    public static void main(String[] args) {
        proxy();
    }
}