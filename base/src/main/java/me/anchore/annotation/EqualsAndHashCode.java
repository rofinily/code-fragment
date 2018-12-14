package me.anchore.annotation;

import me.anchore.log.Loggers;
import me.anchore.util.Checker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anchore
 * @date 2018/9/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EqualsAndHashCode {

    class Builder {
        public static boolean equals(Object o1, Object o2) {
            if (o1 == o2) {
                return true;
            }
            if (o1 == null || o2 == null) {
                return false;
            }
            if (o1.getClass() != o2.getClass()) {
                return false;
            }
            return thisEquals(o1, o2);
        }

        private static boolean thisEquals(Object o1, Object o2) {
            return getAllFields(o1.getClass()).stream().allMatch(field -> {
                field.setAccessible(true);
                try {
                    return Checker.equals(field.get(o1), field.get(o2));
                } catch (IllegalAccessException e) {
                    Loggers.getLogger().error(e);
                    return false;
                }
            });
        }

        public static int hashCode(Object o) {
            int hash = 0;
            if (o == null) {
                return hash;
            }
            Object[] objects = getAllFields(o.getClass()).stream().map(field -> {
                try {
                    field.setAccessible(true);
                    return field.get(o);
                } catch (IllegalAccessException e) {
                    Loggers.getLogger().error(e);
                    return null;
                }
            }).toArray();
            return Arrays.hashCode(objects);
        }

        private static List<Field> getAllFields(Class<?> cls) {
            List<Field> fields = new ArrayList<>();
            if (cls.getSuperclass() != null) {
                fields.addAll(getAllFields(cls.getSuperclass()));
            }
            fields.addAll(Arrays.stream(cls.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(EqualsAndHashCode.class))
                    .collect(Collectors.toList()));
            return fields;
        }
    }
}