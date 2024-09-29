package CacheImpl;

/**
 * Builder class for constructing LFUCache objects with customizable properties.
 */
public class LFUCacheBuilder<K,V> {
    private LFUCache<K,V> lfuCache;

    /**
     * Constructs a new LFUCacheBuilder.
     */
    public LFUCacheBuilder() {
        lfuCache = new LFUCache<K,V>();
    }

    public LFUCacheBuilder<K,V> setCapacity(int capacity) {
        lfuCache.setCapacity(capacity);
        return this;
    }

    public LFUCacheBuilder<K,V> setServerName(String serverName) {
        lfuCache.setServerName(serverName);
        return this;
    }

    /**
     * Builds and returns the configured LFUCache instance.
     *
     * @return Configured LFUCache object.
     */
    public LFUCache<K,V> build() {
        return lfuCache;
    }
}
