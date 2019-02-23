package me.anchore.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author anchore
 * @date 2018/12/24
 */
public class ObjProp {

    private static final String[] PROP_METHOD_PREFIXES = new String[]{"get", "is"};

    private int modifiers;

    private String name;

    private UnaryOperator<Object> value;

    private Annotation[] annotations;

    public ObjProp(Field field) {
        this(field.getModifiers(), field.getName(), o -> {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field.get(o);
            } catch (Exception e) {
                return null;
            }
        }, field.getDeclaredAnnotations());
    }

    public ObjProp(Method method) {
        this(method.getModifiers(), extractPropName(method), o -> {
            try {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method.invoke(o);
            } catch (Exception e) {
                return null;
            }
        }, method.getDeclaredAnnotations());
    }

    public ObjProp(int modifiers, String name, UnaryOperator<Object> value, Annotation[] annotations) {
        this.modifiers = modifiers;
        this.name = name;
        this.value = value;
        this.annotations = annotations;
    }

    public static Predicate<Method> isPropMethod() {
        return ((Predicate<Method>) m -> !Modifier.isStatic(m.getModifiers()))
                .and(m -> m.getParameterTypes().length == 0)
                .and(m -> m.getReturnType() != void.class)
                .and(m -> {
                    String methodName = m.getName();
                    for (String prefix : PROP_METHOD_PREFIXES) {
                        if (methodName.startsWith(prefix) && methodName.length() > prefix.length()
                                && Character.isUpperCase(methodName.charAt(prefix.length()))) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    private static String extractPropName(Method method) {
        String methodName = method.getName();

        for (String prefix : PROP_METHOD_PREFIXES) {
            if (methodName.startsWith(prefix)) {
                String uCasedPropName = methodName.substring(prefix.length());
                return Character.toLowerCase(uCasedPropName.charAt(0)) + uCasedPropName.substring(1);
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getModifiers() {
        return modifiers;
    }

    public Object getValue(Object o) {
        return value.apply(o);
    }

    public <T extends Annotation> T getAnnotation(Class<T> c) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == c) {
                return c.cast(annotation);
            }
        }
        return null;
    }

    public boolean isAnnotationPresent(Class<?> c) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == c) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjProp objProp = (ObjProp) o;

        return Objects.equals(name, objProp.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}