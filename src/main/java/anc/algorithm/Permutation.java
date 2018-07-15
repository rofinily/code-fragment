package anc.algorithm;

import anc.util.Sort;

import java.util.Arrays;

/**
 * @author anchore
 */
public class Permutation {
    private int[] a;

    public Permutation(int[] a) {
        this.a = a;
    }

    public void permutation(int l, int r) {
        if (l >= r) {
            System.out.println(Arrays.toString(a));
            return;
        }
        for (int i = l; i <= r; i++) {
            swapIfNeed(l, i);
            permutation(l + 1, r);
            swapIfNeed(l, i);
        }
    }

    private void swapIfNeed(int p1, int p2) {
        Sort.swapIfNeed(a, p1, p2);
    }

}