package anc;

import java.util.Arrays;

public class Finder {
    private static final Sort.Type DEFAULT_SORT_TYPE = Sort.Type.ASC;

    private int[] a;
    private Sort.Type sortType;

    public Finder(int[] a, Sort.Type sortType) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("array is null");
        }
        this.a = a;
        this.sortType = sortType == null ? DEFAULT_SORT_TYPE : sortType;
    }

    public Finder(int[] a) {
        this(a, DEFAULT_SORT_TYPE);
    }

    public Finder() {
        this(Util.getIntArrayDataSet("d:/dataset"), DEFAULT_SORT_TYPE);
    }

    private void findMth2Nth0(int l, int r, int m, int n) {
        if (l > r)
            return;
        if (l == r && l == m) {
            return;
        }
        int tl = l,
                tr = r,
                p = a[r];
        while (l < r) {
            while (l < r && sortType.compare(a[l], p) >= 0)
                l++;
            a[r] = a[l];
            while (l < r && sortType.compare(a[r], p) < 0)
                r--;
            a[l] = a[r];
        }
        a[l] = p;
        if (l == m) {
            if (m + 1 <= n) {
                findMth2Nth0(l + 1, tr, m + 1, n);
            }
        } else if (l < m) {
            findMth2Nth0(l + 1, tr, m, n);
        } else {
            if (l > n) {
                findMth2Nth0(tl, l - 1, m, n);
            } else {
                findMth2Nth0(tl, l - 1, m, l - 1);
                findMth2Nth0(l, tr, l, n);
            }
        }
    }

    private int fitRange(int i) {
        return i < 0 ? 0 :
                i > a.length - 1 ? a.length - 1 : i;
    }

    public int[] findMth2Nth(int m, int n) {
        m = fitRange(m);
        n = fitRange(n);

        findMth2Nth0(0, a.length - 1, m, n);
        return Arrays.copyOfRange(a, m, n + 1);
    }

    public int findKth(int k) {
        k = fitRange(k);

        findMth2Nth0(0, a.length - 1, k, k);
        return a[k];
    }

    public int[] findTopK(int k) {
        findKth(k - 1);
        return Arrays.copyOfRange(a, 0, k);
    }

    public int[] findSortedTopK(int k) {
        k = fitRange(k - 1);
        return findMth2Nth(0, k);
    }

    public static void main(String[] args) {
        Finder f = new Finder(new int[]{2,1});
        int[] a = f.findSortedTopK(20);
//        f.findMth2Nth(0, f.a.length - 1, 10, 20);
        System.out.println(Arrays.toString(a));
    }

}
