package me.anchore.snippet.algorithm;

import me.anchore.test.DataSetUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class FinderTest {
    private int[] a;
    private Finder f;

    @Before
    public void before() {
        a = DataSetUtil.getIntArray(1000000);
        f = new Finder(a);
    }

    @Ignore
    @Test
    public void testFindMth2Nth() {
        int m = a.length / 10,
                n = a.length / 2;
        f.findMth2Nth(m, n);

        assertTrue(checkResult(a, m, n));
    }

    @Ignore
    @Test
    public void testFindKth() {
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

}
