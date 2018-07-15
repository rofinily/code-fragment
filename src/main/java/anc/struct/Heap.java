package anc.struct;

import anc.util.DataSetUtil;
import anc.util.Sort;

import java.util.Arrays;

public class Heap {
    private int[] a = DataSetUtil.getIntArray(100);
    private Type type = Type.MAX_TOP;

    private enum Type {
        MAX_TOP(Sort.Type.ASC),
        MIN_TOP(Sort.Type.DESC);

        Sort.Type sortType;

        Type(Sort.Type sortType) {
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
        System.out.println(Sort.checkResult(h.a, h.type.sortType.reverse()));
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
        return type.compare(a, b);
    }

    private void swap(int p1, int p2) {
        Sort.swapIfNeed(a, p1, p2);
    }
}