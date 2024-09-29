package CacheImpl;

import Enums.CacheTypeEnum;
import Interfaces.ICache;

import java.text.MessageFormat;

/**
 * Factory class for creating cache instances based on the specified cache type.
 */

public class CacheFactory<K,V> {
    /**
     * Creates an instance of a cache based on the specified type and capacity.
     *
     * @param type     The type of cache to create (FIFO, LFU, LRU).
     * @param capacity The maximum capacity of the cache.
     * @return An instance of the specified cache type.
     */
    public ICache<K,V> createCacheInstance(CacheTypeEnum type, int capacity) {
        switch (type) {
            case FIFO -> {
                return new FIFOCache(capacity);
            }
            case LFU -> {
                LFUCacheBuilder lfuCacheBuilder = new LFUCacheBuilder().setCapacity(capacity).setServerName("localhost:8080");
                return lfuCacheBuilder.build();
            }
            case LRU -> {
                return new LRUCache(capacity);
            }
            default -> {
                System.out.println(MessageFormat.format("Cache with type {0} is not implemented yet.", type));
                return null;
            }
        }
    }
}
