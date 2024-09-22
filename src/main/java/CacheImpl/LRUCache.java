package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of an LRU (Least Recently Used) cache.
 * This cache uses a LinkedHashMap to maintain access order.
 */

public class LRUCache implements ICache {
    private final int capacity;
    private final Map<String, Integer> cache;

    /**
     * Constructs an LRUCache with a specified capacity.
     * Automatically evicts the least recently used item when the capacity is exceeded.
     *
     * @param capacity The maximum number of items the cache can hold.
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<String, Integer>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public void put(String key, Integer value) {
        cache.put(key, value);
    }

    @Override
    public int get(String key) {
        if (!containsKey(key)) {
            System.out.println(MessageFormat.format("ERROR: Key {0} is not in cache.", key));
            return 0;
        }
        return cache.get(key);
    }

    @Override
    public void remove(String key) {
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
    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
