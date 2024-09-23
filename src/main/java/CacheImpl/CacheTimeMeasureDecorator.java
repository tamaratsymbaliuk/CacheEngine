package CacheImpl;

import Interfaces.ICache;

import java.text.MessageFormat;

/**
 * A decorator for caching that measures the time taken for retrieval operations.
 */

public class CacheTimeMeasureDecorator extends CacheDecorator {
    public CacheTimeMeasureDecorator(ICache cache) {
        super(cache);
    }

    @Override
    public int get(String key) {
        long startTime = System.nanoTime();
        int cacheValue = super.get(key);
        long endTime = System.nanoTime();
        System.out.println(MessageFormat.format("Retrieved value for key: {0} in {1} ms", key, (endTime - startTime) / 1000000.0));
        return cacheValue;
    }
}
