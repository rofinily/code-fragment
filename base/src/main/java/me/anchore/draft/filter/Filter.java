package me.anchore.draft.filter;

/**
 * @author anchore
 * @date 2019/6/14
 */
abstract class Filter<V> {
    abstract boolean filter(V v);

    Filter<V> and(final Filter<V> filter) {
        return new Filter<V>() {
            @Override
            boolean filter(V v) {
                return Filter.this.filter(v) && filter.filter(v);
            }
        };
    }

    Filter<V> or(final Filter<V> filter) {
        return new Filter<V>() {
            @Override
            boolean filter(V v) {
                return Filter.this.filter(v) || filter.filter(v);
            }
        };
    }

    Filter<V> not() {
        return new Filter<V>() {
            @Override
            boolean filter(V v) {
                return !Filter.this.filter(v);
            }
        };
    }

}