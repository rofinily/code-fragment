package anc.struct;

import anc.Util;

import java.util.Arrays;

public class Heap {
    private int[] a = Util.getIntArrayDataSet("d:/dataset");
    private final Type TYPE = Type.MAX_TOP;

    private enum Type {
        MAX_TOP(Util.Sort.Type.DESC),
        MIN_TOP(Util.Sort.Type.ASC);

        Util.Sort.Type sortType;

        Type(Util.Sort.Type sortType) {
            this.sortType = sortType;
        }

        int compare(int a, int b) {
            return sortType.compare(a, b);
        }
    }

    public void build() {
        adjust(0, a.length);
    }

    private void adjust(int i, int len) {
        if (isLeaf(i, len)) {
            return;
        }
        if (!isLeaf(left(i), len)) {
            adjust(left(i), len);
        }
        if (compare(a[i], a[left(i)]) > 0) {
            swap(i, left(i));
            adjust(left(i), len);
        }

        if (hasRight(i, len)) {
            if (!isLeaf(right(i), len)) {
                adjust(right(i), len);
            }
            if (compare(a[i], a[right(i)]) > 0) {
                swap(i, right(i));
                adjust(right(i), len);
            }
        }
    }

    public void sort() {
        for (int i = a.length - 1; i > 0; i--) {
            swap(0, i);
            adjust(0, i);
        }
    }

    private boolean check() {
        for (int i = 0, len = a.length; i < len / 2; i++) {
            if (hasLeft(i, len)) {
                if (compare(a[i], a[left(i)]) > 0) {
                    return false;
                }
                if (hasRight(i, len)) {
                    if (compare(a[i], a[right(i)]) > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Heap h = new Heap();
        h.build();
        h.sort();
        System.out.println(Util.checkSortResult(h.a, h.TYPE.sortType));
        System.out.println(Arrays.toString(h.a));
    }

    private boolean hasLeft(int pos, int len) {
        return left(pos) < len;
    }

    private boolean hasRight(int pos, int len) {
        return right(pos) < len;
    }

    private int left(int pos) {
        return (pos << 1) + 1;
    }

    private int right(int pos) {
        return left(pos) + 1;
    }

    private boolean isLeaf(int pos, int len) {
        return !hasLeft(pos, len);
    }

    private int compare(int a, int b) {
        return TYPE.compare(a, b);
    }

    /**
     * a = A, b = B
     * a ^ a = 0
     * b = b ^ 0 = b ^ (a^a) = (a^b) ^ a
     *
     * a = a ^ b; a: A^B, b: B
     * b = a ^ b; a: A^B, b: A^B^B = A
     * a = a ^ b; a: A^B^A = B, b: A
     *
     */
    private void swap(int p1, int p2) {
        a[p1] = a[p1] ^ a[p2];
        a[p2] = a[p1] ^ a[p2];
        a[p1] = a[p1] ^ a[p2];
    }
}
