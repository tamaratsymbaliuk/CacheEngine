package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of a First-In-First-Out (FIFO) cache.
 * This cache evicts the oldest inserted elements when it reaches its capacity.
 */

public class FIFOCache implements ICache {
    private final int capacity;
    private final Map<String, Integer> dictMap;
    private final LinkedList<String> orderQueue;

    /**
     * Constructs a new FIFOCache with the specified capacity.
     *
     * @param capacity The maximum number of items the cache can hold.
     */
    public FIFOCache(int capacity) {
        this.capacity = capacity;
        this.orderQueue = new LinkedList<>();
        this.dictMap = new HashMap<>();
    }

    @Override
    public void put(String key, Integer value) {
        if (containsKey(key)) {
            dictMap.put(key, value);
            orderQueue.remove(key);
            orderQueue.addLast(key);
        } else {
            while (getSize() >= capacity) {
                String oldestItemKey = orderQueue.removeFirst();
                dictMap.remove(oldestItemKey);
            }
            dictMap.put(key, value);
            orderQueue.addLast(key);
        }
    }

    @Override
    public int get(String key) {
        if (containsKey(key)) {
            return dictMap.get(key);
        }
        System.out.println(MessageFormat.format("ERROR: Key {0} is not in the cache", key));
        return 0;
    }

    @Override
    public void remove(String key) {
        if (containsKey(key)) {
            dictMap.remove(key);
            orderQueue.remove(key);
        } else {
            System.out.println(MessageFormat.format("ERROR: Key {0} is not in the cache.", key));
        }
    }

    @Override
    public void clear() {
        dictMap.clear();
        orderQueue.clear();
    }

    @Override
    public int getSize() {
        return dictMap.size();
    }

    @Override
    public boolean containsKey(String key) {
        return dictMap.containsKey(key);
    }
}
