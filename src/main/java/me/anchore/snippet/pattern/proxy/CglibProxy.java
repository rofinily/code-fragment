package me.anchore.snippet.pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author anchore
 * @date 2018/7/15
 */
public class CglibProxy {
    public static void main(String[] args) {
        proxy();
    }

    private static void proxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Object.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            Object returned = proxy.invokeSuper(obj, args);
            if (method.equals(Object.class.getDeclaredMethod("toString"))) {
                return "enhanced: " + returned;
            }
            return returned;
        });
        Object enhanced = enhancer.create();
        System.out.println(enhanced.toString());
    }
}