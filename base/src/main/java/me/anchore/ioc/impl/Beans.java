package me.anchore.ioc.impl;

import me.anchore.ioc.Bean;
import me.anchore.ioc.Bean.Scope;
import me.anchore.ioc.BeanException;
import me.anchore.ioc.BeanFactory;
import me.anchore.ioc.BeanNotFoundException;
import me.anchore.ioc.BeanScanner;
import me.anchore.ioc.Inject;
import me.anchore.ioc.impl.DirectedAcyclicGraph.Node;
import me.anchore.log.Loggers;
import me.anchore.util.Strings;

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

    private Map<String, Object> singletons = new HashMap<>();

    private Map<String, Class<?>> prototypes = new HashMap<>();

    private BeanScanner beanScanner = new ClassPathBeanScanner();

    @Override
    public <T> T getBean(Class<T> c) throws BeanException {
        if (!singletons.containsKey(c.getName())) {
            throw new BeanException(String.format("no such bean of type %s", c.getName()));
        }
        return (T) singletons.get(c.getName());
    }

    @Override
    public <T> T getBean(String beanId) throws BeanException {
        if (!singletons.containsKey(beanId)) {
            throw new BeanException(String.format("no such bean of id %s", beanId));
        }
        return (T) singletons.get(beanId);
    }

    @Override
    public <T> T getBean(String beanId, Object... args) throws BeanException {
        Class<T> c = (Class<T>) prototypes.get(beanId);
        try {
            Constructor<T> constructor = c.getConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanException(e);
        }
    }

    private void init() {
        Map<BeanInfo, Node<BeanInfo>> nodes = new HashMap<>();
        scanBeanInfo(nodes);
        initBean(nodes);
    }

    private void scanBeanInfo(Map<BeanInfo, Node<BeanInfo>> nodes) {
        Set<BeanInfo> beanInfos = beanScanner.scan("me/anchore/ioc");
        for (BeanInfo beanInfo : beanInfos) {
            ClassInfo classInfo = beanInfo.getClassInfo();
            if (!nodes.containsKey(beanInfo)) {
                nodes.put(beanInfo, new Node<>(beanInfo));
            }
            for (FieldInfo f : classInfo.getFieldInfos()) {
                if (f.isAnnotationPresent(Inject.class)) {
                    String injectBeanId = f.getAnnotationInfo(Inject.class).getProperty("value");
                    String beanId = Strings.isEmpty(injectBeanId) ? f.getTypeName() : injectBeanId;
                    BeanInfo fieldBeanInfo = findBeanInfo(beanId, beanInfos);
                    if (!nodes.containsKey(fieldBeanInfo)) {
                        nodes.put(fieldBeanInfo, new Node<>(fieldBeanInfo));
                    }
                    nodes.get(beanInfo).addSource(nodes.get(fieldBeanInfo));
                }
            }
        }
    }

    private BeanInfo findBeanInfo(String beanId, Set<BeanInfo> beanInfos) {
        for (BeanInfo beanInfo : beanInfos) {
            if (beanInfo.getId().equals(beanId)) {
                return beanInfo;
            }
        }
        throw BeanNotFoundException.ofId(beanId);
    }

    private void initBean(Map<BeanInfo, Node<BeanInfo>> nodes) {
        new DirectedAcyclicGraph<>(new HashSet<>(nodes.values())).topoSort(node -> {
            try {
                BeanInfo beanInfo = node.getKey();
                ClassInfo classInfo = beanInfo.getClassInfo();
                Scope scope = Bean.Scope.valueOf(classInfo.getAnnotationInfo(Bean.class).getProperty("scope"));
                Class<?> c = Class.forName(classInfo.getName());
                switch (scope) {
                    case SINGLETON:
                        Constructor<?> constructor = c.getDeclaredConstructor();
                        if (!constructor.isAccessible()) {
                            constructor.setAccessible(true);
                        }
                        Object instance = constructor.newInstance();
                        singletons.put(beanInfo.getId(), instance);
                        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
                            if (fieldInfo.isAnnotationPresent(Inject.class)) {
                                Field field = c.getDeclaredField(fieldInfo.getName());
                                if (!field.isAccessible()) {
                                    field.setAccessible(true);
                                }
                                field.set(instance, singletons.get(fieldInfo.getTypeName()));
                            }
                        }
                        break;
                    case PROTOTYPE:
                        prototypes.put(beanInfo.getId(), c);
                    default:
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

