package me.anchore.beta.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anchore
 * @date 2018/9/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EqualsAndHashcode {
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
            if (!superEquals(o1, o2)) {
                return false;
            }
            return thisEquals(o1, o2);
        }

        private static boolean thisEquals(Object o1, Object o2) {
            List<Field> fields = Arrays.stream(o1.getClass().getFields()).
                    filter(field -> field.isAnnotationPresent(EqualsAndHashcode.class)).
                    collect(Collectors.toList());
            return false;
        }

        private static boolean superEquals(Object o1, Object o2) {
            Class<?> superclass = o1.getClass().getSuperclass();
            if (superclass == null) {
                superclass = o1.getClass();
            }
            try {
                return (boolean) superclass.getDeclaredMethod("equals").invoke(o1);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                return false;
            }
        }
    }
}