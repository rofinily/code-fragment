package anc.algorithm;

import anc.global.DataSetUtil;
import anc.global.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Permutation implements Action {
    int[] a = DataSetUtil.getIntArray(new FileInputStream("d:/dataset"));

    public Permutation() throws FileNotFoundException {
    }

    private void permutation(int l, int r) {
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

    @Override
    public void perform() {
        permutation(0, a.length - 1);
    }

    private void swapIfNeed(int p1, int p2) {
        if (p1 == p2) {
            return;
        }
        if (a[p1] == a[p2]) {
            return;
        }
        Util.swap(a, p1, p2);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Action action = new Permutation();
        action.perform();
    }
}
