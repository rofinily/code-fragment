package me.anchore.annotation;

import me.anchore.log.Loggers;
import me.anchore.util.Checker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * @author anchore
 * @date 2018/9/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EqualsAndHashCode {

    boolean ignore() default false;

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
            return Arrays.stream(o1.getClass().getDeclaredFields()).
                    filter(field -> field.isAnnotationPresent(EqualsAndHashCode.class)).
                    filter(field -> !field.getDeclaredAnnotation(EqualsAndHashCode.class).ignore()).
                    allMatch(field -> {
                        field.setAccessible(true);
                        try {
                            return Checker.equals(field.get(o1), field.get(o2));
                        } catch (IllegalAccessException e) {
                            Loggers.getLogger().error(e);
                            return false;
                        }
                    });
        }

        static void f() {
        }

        public static int hashCode(Object o) {
            int hash = 0;
            if (o == null) {
                return hash;
            }

            return Arrays.stream(o.getClass().getDeclaredFields()).
                    filter(field -> field.isAnnotationPresent(EqualsAndHashCode.class)).
                    filter(field -> !field.getDeclaredAnnotation(EqualsAndHashCode.class).ignore()).
                    mapToInt(field -> {
                        try {
                            field.setAccessible(true);
                            Object f = field.get(o);
                            return f == null ? 0 : f.hashCode();
                        } catch (IllegalAccessException e) {
                            Loggers.getLogger().error(e);
                            return 0;
                        }
                    }).reduce(hash, (a, b) -> 31 * a + b);
        }
    }
}