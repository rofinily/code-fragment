package anc.algorithm;

import anc.util.Sort;

import java.util.Arrays;

/**
 * @author anchore
 */
public class Finder {
    private static final Sort.Type DEFAULT_SORT_TYPE = Sort.Type.ASC;

    private int[] a;
    private Sort.Type sortType;

    public Finder(int[] a, Sort.Type sortType) {
        this.a = a;
        this.sortType = sortType == null ? DEFAULT_SORT_TYPE : sortType;
    }

    public Finder(int[] a) {
        this(a, DEFAULT_SORT_TYPE);
    }

    private void findMth2Nth0(int l, int r, int m, int n) {
        if (l > r) {
            return;
        }
        if (l == r && l == m) {
            return;
        }
        int tl = l,
                tr = r,
                p = a[r];
        while (l < r) {
            while (l < r && sortType.compare(a[l], p) > 0) {
                l++;
            }
            a[r] = a[l];
            while (l < r && sortType.compare(a[r], p) < 0) {
                r--;
            }
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

    public int[] findMth2Nth(int m, int n) {
        findMth2Nth0(0, a.length - 1, m, n);
        return Arrays.copyOfRange(a, m, n + 1);
    }

    public int findKth(int k) {
        findMth2Nth0(0, a.length - 1, k, k);
        return a[k];
    }

    public int[] findTopK(int k) {
        findKth(k - 1);
        return Arrays.copyOfRange(a, 0, k);
    }

    public int[] findSortedTopK(int k) {
        return findMth2Nth(0, k);
    }
}