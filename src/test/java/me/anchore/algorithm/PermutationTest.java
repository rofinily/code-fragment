package me.anchore.algorithm;

import me.anchore.util.DataSetUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class PermutationTest {
    private int[] a;

    @Before
    public void before() {
        a = DataSetUtil.getIntArray(5);
    }

    @Test
    public void testPermutation() {
        Permutation p = new Permutation(a);
        p.permutation(0, a.length - 1);
    }
}