package anc.algorithm;

import anc.util.Sort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author anchore
 * @date 2017/11/7
 */
public class BinarySearcherTest {

    Random r = new Random();
    int[] a;

    @Before
    public void before() {
        a = r.ints(1000, 0, 10000).sorted().toArray();
    }

    @Test
    public void testBinarySearch() {
        int i = r.nextInt(1000);
        Assert.assertEquals(i, BinarySearcher.binarySearch(a, 0, a.length - 1, a[i], Sort.Type.ASC));
    }

    @Test
    public void testMultiBinarySearch() {
        int[] b = r.ints(10, 0, 1000).distinct().toArray();

        Set<Map.Entry<Integer, Integer>> set = BinarySearcher.multiBinarySearch(a, b, Sort.Type.ASC);
        for (Map.Entry<Integer, Integer> entry : set) {
            if (a[entry.getValue()] != entry.getKey()) {
                Assert.fail();
            }
        }
    }
}
