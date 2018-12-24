package me.anchore.util.json;

import me.anchore.annotation.ObjProp;
import me.anchore.annotation.ObjProps;
import me.anchore.util.Checker;
import me.anchore.util.json.annotation.IgnoreJson;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * @author anchore
 * @date 2018/12/15
 */
public class JsonUtil {

    public static <T> T parse(String s, Class<T> c) throws JsonException {
        Scanner sc = new Scanner(s);
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
        appendProperties(o, c, sb);
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
        try {
            Predicate<ObjProp> predicate = prop -> !prop.isAnnotationPresent(IgnoreJson.class);
            predicate = predicate.and(prop -> !Modifier.isTransient(prop.getModifiers()));
            ObjProp[] props = new ObjProps(c, predicate).propStream().toArray(ObjProp[]::new);

            for (ObjProp prop : props) {
                if (o == prop.getValue(o)) {
                    throw new JsonException("Converting circular structure to JSON");
                }
                sb.append('\"').append(prop.getName()).append("\":")
                        .append(stringify(prop.getValue(o))).append(',');
            }
        } catch (StackOverflowError e) {
            // todo 有可能对象太大，比如class loader，一亿个class，会爆栈，考虑递归改成手动栈实现，不过一般不会有这么大的bean吧
            throw new JsonException("Converting circular structure to JSON", e);
        }
    }
}