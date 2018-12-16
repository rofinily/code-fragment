package me.anchore.util.json;

import me.anchore.util.Checker;
import me.anchore.util.json.annotation.JsonIgnore;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author anchore
 * @date 2018/12/15
 */
public class JsonUtil {

    public static <T> T parse(String s) throws JsonException {
        return null;
    }

    public static String stringify(Object o) throws JsonException {
        if (o == null) {
            return "null";
        }

        Class<?> c = o.getClass();

        if (Checker.in(c, boolean.class, Boolean.class,
                byte.class, Byte.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class)) {
            return o.toString();
        }

        if (Checker.in(c,
                char.class, Character.class, CharSequence.class, String.class)) {
            return '\"' + o.toString() + '\"';
        }

        if (c.isEnum()) {
            return '\"' + ((Enum<?>) o).name() + '\"';
        }

        if (c.isArray()) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < Array.getLength(o); i++) {
                sb.append(stringify(Array.get(o, i))).append(',');
            }
            trimLastComma(sb);
            return sb.append(']').toString();
        }

        if (Collection.class.isAssignableFrom(c)) {
            StringBuilder sb = new StringBuilder("[");
            for (Object elem : ((Collection<?>) o)) {
                sb.append(stringify(elem)).append(',');
            }
            trimLastComma(sb);
            return sb.append(']').toString();
        }

        if (Map.class.isAssignableFrom(c)) {
            StringBuilder sb = new StringBuilder("{");
            for (Entry<?, ?> entry : ((Map<?, ?>) o).entrySet()) {
                Object key = entry.getKey();
                if (!(key instanceof CharSequence)) {
                    break;
                }
                sb.append('\"').append((CharSequence) key).append("\":")
                        .append(stringify(entry.getValue())).append(',');
            }
            trimLastComma(sb);
            return sb.append('}').toString();
        }

        StringBuilder sb = new StringBuilder("{");
        try {
            appendProperties(o, c, sb);
        } catch (StackOverflowError e) {
            // todo 有可能对象太大，比如class loader，一亿个class，会爆栈，考虑递归改成手动栈实现，不过一般不会有这么大的bean吧
            throw new JsonException("Converting circular structure to JSON", e);
        }
        trimLastComma(sb);
        return sb.append('}').toString();
    }

    private static void trimLastComma(StringBuilder sb) {
        int lastIdx = sb.length() - 1;
        if (sb.charAt(lastIdx) == ',') {
            sb.deleteCharAt(lastIdx);
        }
    }

    private static void appendProperties(Object o, Class<?> c, StringBuilder sb) throws JsonException {
        if (c == Object.class) {
            return;
        }

        Class<?> superclass = c.getSuperclass();
        if (superclass != Object.class) {
            appendProperties(o, superclass, sb);
        }
        Field[] fields = Arrays.stream(c.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !Modifier.isTransient(field.getModifiers()))
                .filter(field -> !field.isAnnotationPresent(JsonIgnore.class)).toArray(Field[]::new);

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object fieldVal = field.get(o);
                if (o == fieldVal) {
                    throw new JsonException("Converting circular structure to JSON");
                }
                String fieldName = field.getName();
                if (fieldName.startsWith("this$")) {
                    return;
                }
                sb.append('\"').append(fieldName).append("\":")
                        .append(stringify(fieldVal)).append(',');
            } catch (IllegalAccessException e) {
                throw new JsonException("failed to access field " + field, e);
            }
        }


        Method[] methods = Arrays.stream(c.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("get"))
                .filter(method -> method.getReturnType() != void.class)
                .filter(method -> !Modifier.isStatic(method.getModifiers()))
                .filter(method -> !method.isAnnotationPresent(JsonIgnore.class)).toArray(Method[]::new);

        // fixme 处理重复属性，字段
        for (Method method : methods) {
            method.setAccessible(true);
            try {
                Object prop = method.invoke(o);
                if (o == prop) {
                    throw new JsonException("Converting circular structure to JSON");
                }
                String uCasedPropName = method.getName().substring(3);
                String propName = Character.toLowerCase(uCasedPropName.charAt(0)) + uCasedPropName.substring(1);

                sb.append('\"').append(propName).append("\":")
                        .append(stringify(prop)).append(',');
            } catch (IllegalAccessException e) {
                throw new JsonException("failed to access method " + method, e);
            } catch (InvocationTargetException e) {
                throw new JsonException("failed to invoke method " + method, e);
            }
        }
    }
}