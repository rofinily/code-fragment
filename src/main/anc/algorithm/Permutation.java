package anc.algorithm;

import anc.util.DataSetUtil;
import anc.util.Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Permutation implements Action {
    int[] a = DataSetUtil.getIntArray(
            DataSetUtil.getInputStream(new File("d:/dataset"))
    );

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
        Sort.swapIfNeed(a, p1, p2);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Action action = new Permutation();
        action.perform();
    }
}
