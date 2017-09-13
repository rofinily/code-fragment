package anc;

public class Finder {

    void findMth2Nth(int[] a, int l, int r, int m, int n) {
        if (l > r)
            return;
        if (l == r && l == m) {
            return;
        }
        int tl = l,
                tr = r,
                p = a[r];
        while (l < r) {
            while (l < r && a[l] <= p)
                l++;
            a[r] = a[l];
            while (l < r && a[r] > p)
                r--;
            a[l] = a[r];
        }
        a[l] = p;
        if (l == m) {
            if (m + 1 <= n) {
                findMth2Nth(a, l + 1, tr, m + 1, n);
            }
        } else if (l < m) {
            findMth2Nth(a, l + 1, tr, m, n);
        } else {
            if (l > n) {
                findMth2Nth(a, tl, l - 1, m, n);
            } else {
                findMth2Nth(a, tl, l - 1, m, l - 1);
                findMth2Nth(a, l, tr, l, n);
            }
        }
    }

    void findKth(int[] a, int l, int r, int k) {
        findMth2Nth(a, l, r, k, k);
    }
}
