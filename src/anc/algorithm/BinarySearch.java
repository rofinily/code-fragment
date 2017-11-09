package anc.algorithm;

import anc.util.Sort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author anchore
 * @date 2017/11/7
 */
public class BinarySearch {

    public static int search(int[] a, int l, int r, int v, Sort.Type sortType) {
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
            return search(a, l, mid - 1, v, sortType);
        }
        if (sortType.compare(v, a[mid]) > 0) {
            return search(a, mid + 1, r, v, sortType);
        }
        return -1;
    }

    public static <T> int search(T[] a, int l, int r, T v, Comparator<T> c) {
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
            return search(a, l, mid - 1, v, c);
        }
        if (c.compare(v, a[mid]) > 0) {
            return search(a, mid + 1, r, v, c);
        }
        return -1;
    }

    public static <K> Set<Map.Entry<K, Integer>> multiSearch(K[] a, K[] b, Comparator<K> c) {
        Map<K, Integer> result = new HashMap<>();

        TreeMap<K, Integer> pivots = new TreeMap<>(c);
        pivots.put(a[0], 0);
        pivots.put(a[a.length - 1], a.length - 1);

        for (K v : b) {
            Map.Entry<K, Integer> highEntry = pivots.higherEntry(v),
                    lowEntry = pivots.lowerEntry(v);
            int low = lowEntry.getValue();
            int high = highEntry.getValue();
            int i = search0(a, low, high, v, c, pivots);
            if (i != -1) {
                result.put(v, i);
            }
        }
        return result.entrySet();
    }

    public static Set<Map.Entry<Integer, Integer>> multiSearch(int[] a, int[] b, Sort.Type sortType) {
        Map<Integer, Integer> result = new HashMap<>();

        TreeMap<Integer, Integer> pivots = new TreeMap<>(sortType::compare);
        pivots.put(a[0], 0);
        pivots.put(a[a.length - 1], a.length - 1);

        for (int v : b) {
            Map.Entry<Integer, Integer> highEntry = pivots.higherEntry(v),
                    lowEntry = pivots.lowerEntry(v);
            int low = lowEntry.getValue();
            int high = highEntry.getValue();
            int i = search0(a, low, high, v, sortType, pivots);
            if (i != -1) {
                result.put(v, i);
            }
        }

        return result.entrySet();
    }

    private static int search0(int[] a, int l, int r, int v, Sort.Type sortType, TreeMap<Integer, Integer> pivots) {
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

        pivots.put(a[mid], mid);

        if (sortType.compare(a[mid], v) == 0) {
            return mid;
        }

        if (sortType.compare(v, a[mid]) < 0) {
            return search0(a, l, mid - 1, v, sortType, pivots);
        }
        if (sortType.compare(v, a[mid]) > 0) {
            return search0(a, mid + 1, r, v, sortType, pivots);
        }
        return -1;
    }

    private static <T> int search0(T[] a, int l, int r, T v, Comparator<T> c, TreeMap<T, Integer> pivots) {
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
        pivots.put(a[mid], mid);
        if (c.compare(v, a[mid]) == 0) {
            return mid;
        }
        if (c.compare(v, a[mid]) < 0) {
            return search0(a, l, mid - 1, v, c, pivots);
        }
        if (c.compare(v, a[mid]) > 0) {
            return search0(a, mid + 1, r, v, c, pivots);
        }
        return -1;
    }

    static Integer[] box(int[] a) {
        Integer[] ai = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            ai[i] = a[i];
        }
        return ai;
    }

}
