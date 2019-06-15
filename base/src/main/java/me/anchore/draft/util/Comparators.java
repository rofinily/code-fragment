package me.anchore.draft.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * 比较器工厂
 *
 * @author anchore
 */
public class Comparators {
    /**
     * 升序
     * <p>
     * examples:
     * 1.auto type-inference:
     * Comparator<String> c = Comparators.asc()
     * <p>
     * 2.infer the type explicitly:
     * Comparators.<Integer>asc().compare(1, 2)
     *
     * @param <T> type of object to be compared
     * @return result
     */
    public static <T extends Comparable<T>> Comparator<T> asc() {
        return (Comparator<T>) COMPARABLE_ASC;
    }

    public static <T extends Comparable<T>> Comparator<T> desc() {
        return reverse(Comparators.<T>asc());
    }

    public static <T> Comparator<T> reverse(final Comparator<T> c) {
        return (o1, o2) -> c.compare(o2, o1);
    }

    private static final Comparator<? extends Comparable<?>> COMPARABLE_ASC = new Comparator<Comparable<?>>() {
        @Override
        public int compare(Comparable o1, Comparable o2) {
            if (o1 == o2) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            return o1.compareTo(o2);
        }
    };

    public static final Comparator<Number> NUMBER_ASC = (a, b) -> {
        if (a == b) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        // 和数字运算类似，转成精度大的比较
        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            if (a instanceof BigDecimal && b instanceof BigDecimal) {
                return ((BigDecimal) a).compareTo((BigDecimal) b);
            }
            if (a instanceof BigDecimal) {
                return ((BigDecimal) a).compareTo(new BigDecimal(b.toString()));
            }
            return ((BigDecimal) b).compareTo(new BigDecimal(a.toString()));
        }
        if (a instanceof BigInteger || b instanceof BigInteger) {
            if (a instanceof BigInteger && b instanceof BigInteger) {
                return ((BigInteger) a).compareTo((BigInteger) b);
            }
            if (a instanceof BigInteger) {
                return ((BigInteger) a).compareTo(new BigInteger(b.toString()));
            }
            return ((BigInteger) b).compareTo(new BigInteger(a.toString()));
        }
        if (a instanceof Double || b instanceof Double) {
            return Double.compare(a.doubleValue(), b.doubleValue());
        }
        if (a instanceof Float || b instanceof Float) {
            return Float.compare(a.floatValue(), b.floatValue());
        }
        if (a instanceof Long || b instanceof Long) {
            return Long.compare(a.longValue(), b.longValue());
        }
        if (a instanceof Integer || b instanceof Integer) {
            return Integer.compare(a.intValue(), b.intValue());
        }
        if (a instanceof Short || b instanceof Short) {
            return Short.compare(a.shortValue(), b.shortValue());
        }
        if (a instanceof Byte || b instanceof Byte) {
            return Byte.compare(a.byteValue(), b.byteValue());
        }
        throw new IllegalArgumentException("cannot compare " + a.getClass() + " with " + b.getClass());
    };

    public static final Comparable<?> MIN_COMPARABLE = new Comparable<Object>() {
        @Override
        public int compareTo(Object o) {
            return o == this ? 0 : -1;
        }
    };

    public static final Comparable<?> MAX_COMPARABLE = new Comparable<Object>() {
        @Override
        public int compareTo(Object o) {
            return o == this ? 0 : 1;
        }
    };

    private Comparators() {
        throw new InstantiationError();
    }
}