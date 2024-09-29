package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of an LRU (Least Recently Used) cache.
 * This cache uses a LinkedHashMap to maintain access order.
 */

public class LRUCache<K,V> implements ICache<K,V> {
    private final int capacity;
    private final Map<K, V> cache;

    /**
     * Constructs an LRUCache with a specified capacity.
     * Automatically evicts the least recently used item when the capacity is exceeded.
     * The 0.75f is the load factor, controlling how full the map can get before resizing, balancing time and space efficiency.
     * The true parameter sets access order, ensuring the most recently accessed items are kept at the end, which is essential for implementing LRU (Least Recently Used) behavior in the cache.
     *
     * @param capacity The maximum number of items the cache can hold.
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            System.out.println(MessageFormat.format("ERROR: Key {0} is not in cache.", key));
            return null;
        }
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        if (!containsKey(key)) {
            System.out.println(MessageFormat.format("ERROR: Key {0} is not in cache.", key));
        } else {
            cache.remove(key);
        }
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }
    @Override
    public Iterator<K> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}
