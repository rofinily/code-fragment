package me.anchore.draft.filter;

import me.anchore.util.Checker;

/**
 * @author anchore
 * @date 2019/6/14
 */
class FilterImpl {
    static <V> Filter<V> isNull() {
        return new Filter<V>() {
            @Override
            public boolean filter(V o) {
                return o == null;
            }
        };
    }

    static Filter<Number> between() {
        return new Filter<Number>() {
            Number start, end;

            @Override
            public boolean filter(Number o) {
                return o != null && o.doubleValue() >= start.doubleValue() && o.doubleValue() <= end.doubleValue();
            }
        };
    }

    static Filter<?> endsWith(final String s) {
        return new Filter<String>() {
            @Override
            public boolean filter(String o) {
                return o != null && o.endsWith(s);
            }
        };
    }

    static Filter<String> startsWith(final String s) {
        return new Filter<String>() {
            @Override
            public boolean filter(String o) {
                return o != null && o.startsWith(s);
            }
        };
    }

    static Filter<String> like(final String s) {
        return new Filter<String>() {
            @Override
            public boolean filter(String o) {
                return o != null && o.contains(s);
            }
        };
    }

    static <V> Filter<V> empty() {
        return new Filter<V>() {
            @Override
            public boolean filter(V o) {
                return true;
            }
        };
    }

    static Filter<Object> all() {
        return new Filter<Object>() {
            @Override
            public boolean filter(Object o) {
                return false;
            }
        };
    }

    static <V> Filter<V> in(final V... in) {
        return new Filter<V>() {
            @Override
            public boolean filter(V o) {
                return Checker.in(o, in);
            }
        };
    }

    public static void main(String[] args) {
        Filter<String> filter = FilterImpl.<String>isNull().not().and(in("12", "23", "34")).or(startsWith("1"));
        filter.filter(null);
    }
}