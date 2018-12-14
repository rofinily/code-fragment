package me.anchore.util.collection.impl;

import me.anchore.util.collection.Cache;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * @author anchore
 * @date 2018/10/29
 */
public class LruCacheTest {

    @Test
    void test() {
        Cache<Integer, Integer> cache = new LruCache<>(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);

        Assert.assertEquals(3, cache.size());

        for (Entry<Integer, Integer> entry : cache) {
//            Assertions.assertEquals(, );
        }

        int[] i = new ArrayList<Integer>().stream().mapToInt(Integer::intValue).toArray();
    }
}