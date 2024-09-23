package CacheImpl;

import Interfaces.ICache;

/**
 * Decorator class that adds additional functionality to the ICache interface.
 * It delegates all calls to the underlying cache instance.
 */


public class CacheDecorator implements ICache {
    private final ICache cache;

    public CacheDecorator(ICache cache) {
        this.cache = cache;
    }


    @Override
    public void put(String key, Integer value) {
        cache.put(key, value);
    }

    @Override
    public int get(String key) {
        return cache.get(key);
    }

    @Override
    public void remove(String key) {
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
    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
