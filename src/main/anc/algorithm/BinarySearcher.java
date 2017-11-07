package anc.algorithm;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author anchore
 * @date 2017/11/7
 */
public class BinarySearcher {

    public static Set<Map.Entry<Integer, Integer>> multiBinarySearch(int[] a, int l, int r, int[] b) {
        TreeMap<Integer, Integer> pivots = new TreeMap<>();

        pivots.put(a[l], l);
        pivots.put(a[r], r);

        for (int v : b) {
            int high = pivots.higherEntry(v).getValue();
            int low = pivots.lowerEntry(v).getValue();
            int i = binarySearch(a, low, high, v);
            if (i != -1) {
                pivots.put(v, i);
            }
        }
        return pivots.entrySet();
    }

    public static int binarySearch(int[] a, int l, int r, int v) {
        if (l > r) {
            return -1;
        }
        if (l == r) {
            if (a[l] == v) {
                return l;
            }
            return -1;
        }
        int mid = (l + r) >> 1;
        if (a[mid] == v) {
            return mid;
        }
        if (v < a[mid]) {
            return binarySearch(a, l, mid - 1, v);
        }
        if (v > a[mid]) {
            return binarySearch(a, mid + 1, r, v);
        }
        return -1;
    }

}
