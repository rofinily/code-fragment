package me.anchore.ioc.impl;

import me.anchore.ioc.BeanException;
import me.anchore.ioc.BeanFactory;
import me.anchore.ioc.BeanNotFoundException;
import me.anchore.ioc.BeanScanner;
import me.anchore.ioc.Inject;
import me.anchore.ioc.impl.DirectedAcyclicGraph.Node;
import me.anchore.log.Loggers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author anchore
 * @date 2019/2/13
 */
public class Beans implements BeanFactory {

    private Map<String, Object> idToBean = new HashMap<>();

    private BeanScanner beanScanner = new ClassPathBeanScanner();

    @Override
    public <T> T getBean(Class<T> c) throws BeanException {
        if (!idToBean.containsKey(c.getName())) {
            throw new BeanException(String.format("no such bean of type %s", c.getName()));
        }
        return (T) idToBean.get(c.getName());
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
        Map<ClassInfo, Node<ClassInfo>> nodes = new HashMap<>();
        scanBeanInfo(nodes);
        initBean(nodes);
    }

    private void scanBeanInfo(Map<ClassInfo, Node<ClassInfo>> nodes) {
        Set<BeanInfo> beanInfos = beanScanner.scan("me.anchore.ioc");
        for (BeanInfo beanInfo : beanInfos) {
            ClassInfo classInfo = beanInfo.getClassInfo();
            if (!nodes.containsKey(classInfo)) {
                nodes.put(classInfo, new Node<>(classInfo));
            }
            for (FieldInfo f : classInfo.getFieldInfos()) {
                if (f.isAnnotationPresent(Inject.class)) {
                    ClassInfo fieldClassInfo = findClassInfo(f.getTypeName(), beanInfos);
                    if (!nodes.containsKey(fieldClassInfo)) {
                        nodes.put(fieldClassInfo, new Node<>(fieldClassInfo));
                    }
                    nodes.get(classInfo).addSource(nodes.get(fieldClassInfo));
                }
            }
        }
    }

    private ClassInfo findClassInfo(String typeName, Set<BeanInfo> beanInfos) {
        for (BeanInfo beanInfo : beanInfos) {
            ClassInfo classInfo = beanInfo.getClassInfo();
            if (typeName.equals(classInfo.getName())) {
                return classInfo;
            }
        }
        throw BeanNotFoundException.ofType(typeName);
    }

    private void initBean(Map<ClassInfo, Node<ClassInfo>> nodes) {
        new DirectedAcyclicGraph<>(new HashSet<>(nodes.values())).topoSort(node -> {
            try {
                ClassInfo classInfo = node.getKey();
                Class<?> c = Class.forName(classInfo.getName());
                Constructor<?> constructor = c.getDeclaredConstructor();
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                Object instance = constructor.newInstance();
                idToBean.put(classInfo.getName(), instance);

                for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
                    if (fieldInfo.isAnnotationPresent(Inject.class)) {
                        Field field = c.getDeclaredField(fieldInfo.getName());
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(instance, idToBean.get(fieldInfo.getTypeName()));
                    }
                }
            } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                Loggers.getLogger().error(e);
            }
        });
    }

    private static final Beans INSTANCE = new Beans();

    private Beans() {
        init();
    }

    public static Beans get() {
        return INSTANCE;
    }
}

