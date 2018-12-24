package me.anchore.annotation;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author anchore
 * @date 2018/12/23
 */
public class ObjProps {

    private Set<ObjProp> props = new LinkedHashSet<>();

    public ObjProps(Class<?> c, Predicate<ObjProp> propFilter) {
        init(c, propFilter);
    }

    public Stream<ObjProp> propStream() {
        return props.stream();
    }

    private void init(Class<?> c, Predicate<ObjProp> propFilter) {
        if (c == null || c == Object.class) {
            return;
        }

        init(c.getSuperclass(), propFilter);

        props.addAll(Arrays.stream(c.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !field.getName().startsWith("this$"))
                .map(ObjProp::new)
                .filter(propFilter)
                .collect(Collectors.toList()));

        props.addAll(Arrays.stream(c.getDeclaredMethods())
                .filter(ObjProp.isPropMethod())
                .map(ObjProp::new)
                .filter(prop -> !props.contains(prop))
                .filter(propFilter)
                .collect(Collectors.toList()));
    }

}