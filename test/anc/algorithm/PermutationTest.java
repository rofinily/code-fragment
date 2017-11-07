package anc.algorithm;

import anc.util.DataSetUtil;
import org.junit.Before;
import org.junit.Test;

public class PermutationTest {
    private int[] a;

    @Before
    public void initDataSet() {
        a = DataSetUtil.getIntArray(10);
    }

    @Test
    public void testPermutation() {
        Permutation p = new Permutation(a);
        p.permutation(0, a.length - 1);
    }
}