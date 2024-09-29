package CacheImpl;

import Interfaces.ICache;

import java.util.Iterator;

/**
 * Decorator class that adds additional functionality to the ICache interface.
 * It delegates all calls to the underlying cache instance.
 */


public class CacheDecorator<K,V> implements ICache<K,V> {
    private final ICache<K,V> cache;

    public CacheDecorator(ICache<K,V> cache) {
        this.cache = cache;
    }

    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.getSize();
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
