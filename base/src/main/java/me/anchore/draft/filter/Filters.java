package me.anchore.draft.filter;

import me.anchore.draft.util.Comparators;
import me.anchore.util.Checker;

import java.util.Objects;

/**
 * @author anchore
 * @date 2019/6/14
 */
public class Filters {

    public static <V> ValueFilter<V> empty() {
        return o -> true;
    }

    public static <V> ValueFilter<V> all() {
        return Filters.<V>empty().not();
    }

    public static <V> ValueFilter<V> isNull() {
        return Objects::isNull;
    }

    public static <V> ValueFilter<V> equalsTo(V v) {
        return o -> Objects.equals(o, v);
    }

    public static <V> ValueFilter<V> in(final V... in) {
        return o -> Checker.in(o, (Object[]) in);
    }

    public static ValueFilter<Number> between(Number start, Number end) {
        return greaterThan(start, true).and(lessThan(end, true));
    }

    public static ValueFilter<Number> greaterThan(Number n, boolean equal) {
        return o -> equal ? Comparators.NUMBER_ASC.compare(o, n) >= 0 : Comparators.NUMBER_ASC.compare(o, n) > 0;
    }

    public static ValueFilter<Number> lessThan(Number n, boolean equal) {
        return greaterThan(n, equal).not();
    }

    public static ValueFilter<?> endsWith(final String s) {
        return (ValueFilter<String>) o -> o != null && o.endsWith(s);
    }

    public static ValueFilter<String> startsWith(final String s) {
        return o -> o != null && o.startsWith(s);
    }

    public static ValueFilter<String> contains(final CharSequence s) {
        return o -> o != null && o.contains(s);
    }

    private Filters() {
    }
}