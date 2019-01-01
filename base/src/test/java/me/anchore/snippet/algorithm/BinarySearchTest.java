package me.anchore.snippet.algorithm;

import me.anchore.util.Sort;
import org.junit.Before;
import org.junit.Ignore;
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
public class BinarySearchTest {

    Random r = new Random();
    int[] a;

    public static void test() {
        Random r = new Random();
        int size = 1000000;
        int[] a = r.ints(size, 0, size).sorted().toArray();
        Integer[] ai = BinarySearch.box(a);
        int[] b = r.ints(10, 0, size).distinct().toArray();
        Integer[] bi = BinarySearch.box(b);

        Sort.Type sortType = Sort.Type.ASC;

        long start = System.nanoTime();
        BinarySearch.multiSearch(a, b, sortType);
        long cost = System.nanoTime() - start;

        start = System.nanoTime();
        BinarySearch.multiSearch(ai, bi, Integer::compareTo);
        long cost1 = System.nanoTime() - start;
        System.out.println(cost * 1.0 / cost1);
    }

    @Before
    public void before() {
        int size = /*Integer.MAX_VALUE >> */3;
        a = r.ints(size, 0, size << 1).sorted().toArray();
    }

    @Ignore
    @Test
    public void testBinarySearch() {
        int i = r.nextInt(1000);
        assertEquals(i, BinarySearch.search(a, 0, a.length - 1, a[i], Sort.Type.ASC));
    }

    @Ignore
    @Test
    public void testMultiBinarySearch() {
        int[] idx = r.ints(10, 0, a.length).distinct().toArray();

        int[] b = new int[idx.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[idx[i]];
        }

        Set<Map.Entry<Integer, Integer>> set = BinarySearch.multiSearch(a, b, Sort.Type.ASC);

        int hit = 0;
        for (int bv : b) {
            if (BinarySearch.search(a, 0, a.length - 1, bv, Sort.Type.ASC) >= 0) {
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
