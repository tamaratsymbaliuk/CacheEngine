package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;

/**
 * A decorator for caching that measures the time taken for retrieval operations.
 */

public class CacheTimeMeasureDecorator<K,V> extends CacheDecorator<K,V> {
    public CacheTimeMeasureDecorator(ICache<K,V> cache) {
        super(cache);
    }

    @Override
    public V get(K key) {
        long startTime = System.nanoTime();
        V cacheValue = super.get(key);
        long endTime = System.nanoTime();
        System.out.println(MessageFormat.format("Retrieved value for key: {0} in {1} ms", key, (endTime - startTime) / 1000000.0));
        return cacheValue;
    }
}
