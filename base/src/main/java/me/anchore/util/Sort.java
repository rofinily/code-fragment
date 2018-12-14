package me.anchore.util;

import java.util.Comparator;

/**
 * @author anchore
 */
public class Sort {
    /**
     * @param a    array
     * @param type sort type
     * @return true if array is sorted with specific sort type, else false
     */
    public static boolean checkResult(int[] a, Type type) {
        if (a.length < 2) {
            return true;
        }
        for (int i = 0, len = a.length; i < len - 1; i++) {
            if (type.compare(a[i], a[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param a  array
     * @param p1 position1
     * @param p2 position2
     * @return true if swapped, else false
     * @see Sort#swap(int[], int, int)
     */
    public static boolean swapIfNeed(int[] a, int p1, int p2) {
        if (p1 == p2) {
            return false;
        }
        if (a[p1] == a[p2]) {
            return false;
        }
        swap(a, p1, p2);
        return true;
    }

    /**
     * a = A, b = B
     * a ^ a = 0
     * b = b ^ 0 = b ^ (a^a) = (a^b) ^ a
     * <p>
     * a = a ^ b; a: A^B, b: B
     * b = a ^ b; a: A^B, b: A^B^B = A
     * a = a ^ b; a: A^B^A = B, b: A
     */
    public static void swap(int[] a, int p1, int p2) {
        a[p1] = a[p1] ^ a[p2];
        a[p2] = a[p1] ^ a[p2];
        a[p1] = a[p1] ^ a[p2];
    }

    public enum Type {
        /**
         * ascend order
         */
        ASC() {
            @Override
            public int compare(byte a, byte b) {
                return a - b;
            }

            @Override
            public int compare(short a, short b) {
                return a - b;
            }

            @Override
            public int compare(int a, int b) {
                return a - b;
            }

            @Override
            public int compare(float a, float b) {
                return Float.compare(a, b);
            }

            @Override
            public int compare(long a, long b) {
                return Long.compare(a, b);
            }

            @Override
            public int compare(double a, double b) {
                return Double.compare(a, b);
            }

            @Override
            public <T extends Comparable<T>> int compare(T a, T b) {
                return a.compareTo(b);
            }

            @Override
            public <T> int compare(T a, T b, Comparator<T> c) {
                return c.compare(a, b);
            }
        },
        /**
         * descend order
         */
        DESC() {
            @Override
            public int compare(byte a, byte b) {
                return ASC.compare(b, a);
            }

            @Override
            public int compare(short a, short b) {
                return ASC.compare(b, a);
            }

            @Override
            public int compare(int a, int b) {
                return ASC.compare(b, a);
            }

            @Override
            public int compare(float a, float b) {
                return ASC.compare(b, a);
            }

            @Override
            public int compare(long a, long b) {
                return ASC.compare(b, a);
            }

            @Override
            public int compare(double a, double b) {
                return ASC.compare(b, a);
            }

            @Override
            public <T extends Comparable<T>> int compare(T a, T b) {
                return ASC.compare(b, a);
            }

            @Override
            public <T> int compare(T a, T b, Comparator<T> c) {
                return ASC.compare(b, a, c);
            }
        };

        public abstract int compare(byte a, byte b);

        public abstract int compare(short a, short b);

        public abstract int compare(int a, int b);

        public abstract int compare(float a, float b);

        public abstract int compare(long a, long b);

        public abstract int compare(double a, double b);

        public abstract <T extends Comparable<T>> int compare(T a, T b);

        public abstract <T> int compare(T a, T b, Comparator<T> c);

        public Type reverse() {
            return this == ASC ? DESC : this;
        }
    }
}