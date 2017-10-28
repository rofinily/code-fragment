package anc.algorithm;

import anc.TestAction;
import anc.util.DataSetUtil;
import org.junit.Test;

import java.nio.file.Paths;

public class PermutationTest implements TestAction {
    private int[] a;

    @Test
    public void testPermutation() {
        beforeOperate();
        Permutation p = new Permutation(a);
        p.permutation(0, a.length - 1);
    }

    @Override
    public void initDataSet() {
        a = DataSetUtil.getIntArray(
                DataSetUtil.getInputStream(Paths.get("d:/dataset"))
        );
    }
}