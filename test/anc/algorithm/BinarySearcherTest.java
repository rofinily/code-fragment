package anc.algorithm;

import anc.util.Sort;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        assertEquals(i, BinarySearcher.binarySearch(a, 0, a.length - 1, a[i], Sort.Type.ASC));
    }

    @Test
    public void testMultiBinarySearch() {
        int[] b = r.ints(10, 0, 1000).distinct().sorted().toArray();

        Set<Map.Entry<Integer, Integer>> set = BinarySearcher.multiBinarySearch(a, b, Sort.Type.ASC);

        int hit = 0;
        for (int bv : b) {
            if (BinarySearcher.binarySearch(a, 0, a.length - 1, bv, Sort.Type.ASC) >= 0) {
                hit++;
            }
        }
        assertEquals(hit, set.size());

        for (Map.Entry<Integer, Integer> entry : set) {
            if (a[entry.getValue()] != entry.getKey()) {
                fail();
            }
        }
    }
}
