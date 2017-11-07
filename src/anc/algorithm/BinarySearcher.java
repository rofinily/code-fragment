package anc.algorithm;

import anc.util.Sort;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author anchore
 * @date 2017/11/7
 */
public class BinarySearcher {

    public static Set<Map.Entry<Integer, Integer>> multiBinarySearch(int[] a, int[] b, Sort.Type sortType) {
        TreeMap<Integer, Integer> pivots = new TreeMap<>(sortType::compare);

        for (int v : b) {
            Map.Entry<Integer, Integer> highEntry = pivots.higherEntry(v),
                    lowEntry = pivots.lowerEntry(v);
            int low = lowEntry == null ? 0 : lowEntry.getValue();
            int high = highEntry == null ? a.length - 1 : highEntry.getValue();
            int i = binarySearch(a, low, high, v, sortType);
            if (i != -1) {
                pivots.put(v, i);
            }
        }
        return pivots.entrySet();
    }

    public static int binarySearch(int[] a, int l, int r, int v, Sort.Type sortType) {
        if (l > r) {
            return -1;
        }
        if (l == r) {
            if (sortType.compare(a[l], v) == 0) {
                return l;
            }
            return -1;
        }
        int mid = (l + r) >> 1;
        if (sortType.compare(a[mid], v) == 0) {
            return mid;
        }
        if (sortType.compare(v, a[mid]) < 0) {
            return binarySearch(a, l, mid - 1, v, sortType);
        }
        if (sortType.compare(v, a[mid]) > 0) {
            return binarySearch(a, mid + 1, r, v, sortType);
        }
        return -1;
    }


    public static <K> Set<Map.Entry<K, Integer>> multiBinarySearch(K[] a, K[] b, Comparator<K> c) {
        TreeMap<K, Integer> pivots = new TreeMap<>();

        for (K v : b) {
            Map.Entry<K, Integer> highEntry = pivots.higherEntry(v),
                    lowEntry = pivots.lowerEntry(v);
            int low = lowEntry == null ? 0 : lowEntry.getValue();
            int high = highEntry == null ? a.length - 1 : highEntry.getValue();
            int i = binarySearch(a, low, high, v, c);
            if (i != -1) {
                pivots.put(v, i);
            }
        }
        return pivots.entrySet();
    }

    public static <T> int binarySearch(T[] a, int l, int r, T v, Comparator<T> c) {
        if (l > r) {
            return -1;
        }
        if (l == r) {
            if (c.compare(v, a[l]) == 0) {
                return l;
            }
            return -1;
        }
        int mid = (l + r) >> 1;
        if (c.compare(v, a[mid]) == 0) {
            return mid;
        }
        if (c.compare(v, a[mid]) < 0) {
            return binarySearch(a, l, mid - 1, v, c);
        }
        if (c.compare(v, a[mid]) > 0) {
            return binarySearch(a, mid + 1, r, v, c);
        }
        return -1;
    }

    private static Integer[] box(int[] a) {
        Integer[] ai = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            ai[i] = a[i];
        }
        return ai;
    }

    public static void main(String[] args) {
        Random r = new Random();
        int size = 1000000;
        int[] a = r.ints(size, 0, size).sorted().toArray();
        Integer[] ai = box(a);
        int[] b = r.ints(10, 0, size).distinct().toArray();
        Integer[] bi = box(b);

        Sort.Type sortType = Sort.Type.ASC;

        long start = System.nanoTime();
        multiBinarySearch(a, b, sortType);
        long cost = System.nanoTime() - start;

        start = System.nanoTime();
        multiBinarySearch(ai, bi, Integer::compareTo);
        long cost1 = System.nanoTime() - start;
        System.out.println(cost * 1.0 / cost1);
    }

}
