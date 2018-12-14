package me.anchore.util.collection.impl;

import me.anchore.util.Pair;
import me.anchore.util.collection.Cache;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author anchore
 * @date 2018/10/29
 */
public class LruCache<K, V> implements Cache<K, V> {

    private Map<K, V> cache;

    private Deque<K> keys;

    private int capacity;

    public LruCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>(capacity, 1);
        keys = new ArrayDeque<>(capacity);
    }

    @Override
    public void put(K k, V v) {

        if (cache.containsKey(k)) {
            cache.put(k, v);
            keys.remove(k);
            keys.addFirst(k);
        } else {
            cache.put(k, v);
            if (cache.size() > capacity) {
                K evicted = keys.removeLast();
                cache.remove(evicted);
                keys.addLast(k);
            }
            keys.addLast(k);
        }
    }

    @Override
    public V get(K k) {
        keys.remove(k);
        keys.addFirst(k);
        return cache.get(k);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {

            private Iterator<K> keys = LruCache.this.keys.iterator();

            @Override
            public boolean hasNext() {
                return keys.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                K next = keys.next();
                return Pair.of(next, cache.get(next));
            }
        };
    }


}