package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementation of a Least Frequently Used (LFU) cache.
 * This cache evicts the least frequently accessed elements when it reaches its capacity.
 */
public class LFUCache implements ICache {
    private int capacity;
    private String serverName;
    private final Map<String, LFUCacheItem> keyToCacheItemMap;
    private final PriorityQueue<LFUCacheItem> keyFrequenciesMinHeap;

    /**
     * This constructor uses a default capacity if not specified by the builder
     */
    public LFUCache() {
        this.capacity = 10;
        this.keyToCacheItemMap = new HashMap<>();
        this.keyFrequenciesMinHeap = new PriorityQueue<>(Comparator.comparingInt(value -> value.getFrequency()));
    }

    @Override
    public void put(String key, Integer value) {
        if (containsKey(key)) {
            LFUCacheItem node = keyToCacheItemMap.get(key);
            LFUCacheItem clonedNode = node.clone();
            clonedNode.setValue(value);
            keyToCacheItemMap.put(key, clonedNode);
            increaseCacheItemFrequency(node, clonedNode);
            return;
        }
        while (getSize() >= capacity) {
            LFUCacheItem nodeWithMinFrequency = keyFrequenciesMinHeap.poll();
            keyToCacheItemMap.remove(nodeWithMinFrequency.getKey());
        }
        LFUCacheItem nodeToAdd = new LFUCacheItem(key, value);
        keyToCacheItemMap.put(key, nodeToAdd);
        keyFrequenciesMinHeap.add(nodeToAdd);
    }

    @Override
    public int get(String key) {
        if (containsKey(key)) {
            LFUCacheItem node = keyToCacheItemMap.get(key);
            LFUCacheItem clonedNode = node.clone();
            keyToCacheItemMap.put(key, clonedNode);
            increaseCacheItemFrequency(node, clonedNode);
            return clonedNode.getValue();
        }
        System.out.println(MessageFormat.format("ERROR: Key {0} is not in cache.", key));
        return 0;
    }

    @Override
    public void remove(String key) {
        if (containsKey(key)) {
            LFUCacheItem node = keyToCacheItemMap.get(key);
            keyToCacheItemMap.remove(key);
            keyFrequenciesMinHeap.remove(node);
        } else {
            System.out.println(MessageFormat.format("ERROR: Key {0} is not in the cache.", key));
        }
    }

    @Override
    public void clear() {
        keyToCacheItemMap.clear();
        keyFrequenciesMinHeap.clear();
    }

    @Override
    public int getSize() {
        return keyToCacheItemMap.size();
    }

    @Override
    public boolean containsKey(String key) {
        return keyToCacheItemMap.containsKey(key);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    private void increaseCacheItemFrequency(LFUCacheItem nodeToDelete, LFUCacheItem nodeToAdd) {
        keyFrequenciesMinHeap.remove(nodeToDelete);
        nodeToAdd.setFrequency(nodeToAdd.getFrequency() + 1);
        keyFrequenciesMinHeap.add(nodeToAdd);
    }
}
