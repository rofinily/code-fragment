package anc.algorithm;

import anc.util.DataSetUtil;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

class FinderTest implements TestAction {
    private int[] a;
    private Finder f;

    @Test
    public void testFindMth2Nth() throws Exception {
        beforeOperate();

        f = new Finder(a);
        int m = a.length / 10,
                n = a.length / 2;
        f.findMth2Nth(m, n);

        assertTrue(checkResult(a, m, n));
    }

    @Test
    public void testFindKth() throws FileNotFoundException {
        beforeOperate();

        f = new Finder(a);
        int k = a.length / 2;
        f.findMth2Nth(k, k);

        assertTrue(checkResult(a, k, k));
    }

    boolean checkResult(int[] a, int m, int n) {
        int[] aCopy = Arrays.copyOf(a, a.length);
        Arrays.sort(aCopy);
        for (int i = m; i <= n; i++) {
            if (a[i] != aCopy[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void initDataSet() {
        a = DataSetUtil.getIntArray(
                DataSetUtil.getInputStream(Paths.get("d:/dataset"))
        );
    }
}
