package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of a First-In-First-Out (FIFO) cache.
 * This cache evicts the oldest inserted elements when it reaches its capacity.
 */

public class FIFOCache<K,V> implements ICache<K,V> {
    private final int capacity;
    private final Map<K, V> dictMap;
    private final LinkedList<K> orderQueue;

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
    public void put(K key, V value) {
        if (containsKey(key)) {
            dictMap.put(key, value);
            // update the order of the key from queue
            orderQueue.remove(key);
            orderQueue.addLast(key);
        } else {
            while (getSize() >= capacity) {
                K oldestItemKey = orderQueue.removeFirst();
                dictMap.remove(oldestItemKey);
            }
            dictMap.put(key, value);
            orderQueue.addLast(key);
        }
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            return dictMap.get(key);
        }
        System.out.println(MessageFormat.format("ERROR: Key {0} is not in the cache", key));
        return null;
    }

    @Override
    public void remove(K key) {
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
    public boolean containsKey(K key) {
        return dictMap.containsKey(key);
    }
    @Override
    public Iterator<K> iterator() {
        //return queue.iterator();
        return orderQueue.iterator();
    }
}

