package anc.util;

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
            if (type.compare(a[i], a[i + 1]) < 0) {
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

    public enum Type implements ICompare {
        ASC() {
            @Override
            public int compare(int a, int b) {
                return b - a;
            }
        },
        DESC() {
            @Override
            public int compare(int a, int b) {
                return a - b;
            }
        };

        public Type reverse() {
            return this == ASC ? DESC : this;
        }
    }
}