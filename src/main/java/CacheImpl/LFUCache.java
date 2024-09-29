package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;
import java.util.*;

/**
 * Implementation of a Least Frequently Used (LFU) cache.
 * This cache evicts the least frequently accessed elements when it reaches its capacity.
 */
public class LFUCache<K,V> implements ICache<K,V> {
    private int capacity;
    private String serverName;
    private final Map<K, LFUCacheItem<K,V>> keyToCacheItemMap;
    private final PriorityQueue<LFUCacheItem<K,V>> keyFrequenciesMinHeap;

    /**
     * This constructor uses a default capacity if not specified by the builder
     */
    public LFUCache() {
        this.capacity = 10;
        this.keyToCacheItemMap = new HashMap<>();
        this.keyFrequenciesMinHeap = new PriorityQueue<>(Comparator.comparingInt(value -> value.getFrequency()));
    }

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            LFUCacheItem<K,V> node = keyToCacheItemMap.get(key);
            LFUCacheItem<K,V> clonedNode = node.clone();
            clonedNode.setValue(value);
            keyToCacheItemMap.put(key, clonedNode);
            increaseCacheItemFrequency(node, clonedNode);
            return;
        }
        while (getSize() >= capacity) {
            LFUCacheItem<K,V> nodeWithMinFrequency = keyFrequenciesMinHeap.poll();
            keyToCacheItemMap.remove(nodeWithMinFrequency.getKey());
        }
        LFUCacheItem<K,V> nodeToAdd = new LFUCacheItem<K,V>(key, value);
        keyToCacheItemMap.put(key, nodeToAdd);
        keyFrequenciesMinHeap.add(nodeToAdd);
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            LFUCacheItem<K,V> node = keyToCacheItemMap.get(key);
            LFUCacheItem<K,V> clonedNode = node.clone();
            keyToCacheItemMap.put(key, clonedNode);
            increaseCacheItemFrequency(node, clonedNode);
            return clonedNode.getValue();
        }
        System.out.println(MessageFormat.format("ERROR: Key {0} is not in cache.", key));
        return null;
    }

    @Override
    public void remove(K key) {
        if (containsKey(key)) {
            LFUCacheItem<K,V> node = keyToCacheItemMap.get(key);
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
    public boolean containsKey(K key) {
        return keyToCacheItemMap.containsKey(key);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    private void increaseCacheItemFrequency(LFUCacheItem<K,V> nodeToDelete, LFUCacheItem<K,V> nodeToAdd) {
        keyFrequenciesMinHeap.remove(nodeToDelete);
        nodeToAdd.setFrequency(nodeToAdd.getFrequency() + 1);
        keyFrequenciesMinHeap.add(nodeToAdd);
    }
    @Override
    public Iterator<K> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}
