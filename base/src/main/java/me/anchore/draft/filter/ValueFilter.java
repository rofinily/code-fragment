package me.anchore.draft.filter;

/**
 * @author anchore
 * @date 2019/6/14
 */
public interface ValueFilter<V> {
    boolean filter(V v);

    default ValueFilter<V> and(final ValueFilter<V> valueFilter) {
        return v -> ValueFilter.this.filter(v) && valueFilter.filter(v);
    }

    default ValueFilter<V> or(final ValueFilter<V> valueFilter) {
        return v -> ValueFilter.this.filter(v) || valueFilter.filter(v);
    }

    default ValueFilter<V> not() {
        return v -> !ValueFilter.this.filter(v);
    }

}