package me.anchore.ioc.impl;

import me.anchore.ioc.BeanException;
import me.anchore.ioc.BeanFactory;
import me.anchore.ioc.BeanScanner;
import me.anchore.ioc.Inject;
import me.anchore.ioc.impl.DirectedAcyclicGraph.Node;
import me.anchore.log.Loggers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * @author anchore
 * @date 2019/2/13
 */
public class Beans implements BeanFactory {

    private Map<String, Object> idToBean = new HashMap<>();

    private Map<Class<?>, Object> classToBean = new HashMap<>();

    private BeanScanner beanScanner = new ClassPathBeanScanner();

    @Override
    public <T> T getBean(Class<T> c) throws BeanException {
        if (!classToBean.containsKey(c)) {
            throw new BeanException(String.format("no such bean of type %s", c.getName()));
        }
        return (T) classToBean.get(c);
    }

    @Override
    public <T> T getBean(String beanId) throws BeanException {
        if (!idToBean.containsKey(beanId)) {
            throw new BeanException(String.format("no such bean of id %s", beanId));
        }
        return (T) idToBean.get(beanId);
    }

    @Override
    public <T> T getBean(String beanId, Object... args) throws BeanException {
        Class<T> c = (Class<T>) idToBean.get(beanId);
        try {
            Constructor<T> constructor = c.getConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new BeanException(e);
        }
    }

    private void init() {
        Map<Class<?>, Node<Class<?>>> nodes = new HashMap<>();

        scanBeanInfo(nodes);
        initBean(nodes);
    }

    private void scanBeanInfo(Map<Class<?>, Node<Class<?>>> nodes) {
        beanScanner.scan("me.anchore").stream().map(BeanInfo::getClassName)
                .map(className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .forEach(c -> {
                    if (!nodes.containsKey(c)) {
                        nodes.put(c, new Node<>(c));
                    }
                    for (Field f : c.getDeclaredFields()) {
                        if (f.isAnnotationPresent(Inject.class)) {
                            Class<?> fieldClass = f.getType();
                            if (!nodes.containsKey(fieldClass)) {
                                nodes.put(fieldClass, new Node<>(fieldClass));
                            }
                            nodes.get(c).addSource(nodes.get(fieldClass));
                        }
                    }
                });
    }

    private void initBean(Map<Class<?>, Node<Class<?>>> nodes) {
        new DirectedAcyclicGraph<>(new HashSet<>(nodes.values())).topoSort(node -> {
            try {
                Object instance = node.getKey().newInstance();
                classToBean.put(node.getKey(), instance);

                for (Field f : node.getKey().getDeclaredFields()) {
                    if (f.isAnnotationPresent(Inject.class)) {
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        f.set(instance, classToBean.get(f.getType()));
                    }
                }
            } catch (InstantiationException | IllegalAccessException e) {
                Loggers.getLogger().error(e);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        Beans.get().init();
    }

    private static final Beans INSTANCE = new Beans();

    private Beans() {
    }

    public static Beans get() {
        return INSTANCE;
    }
}

