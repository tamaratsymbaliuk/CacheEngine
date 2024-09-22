package CacheImpl;

/**
 * Builder class for constructing LFUCache objects with customizable properties.
 */
public class LFUCacheBuilder {
    private LFUCache lfuCache;

    /**
     * Constructs a new LFUCacheBuilder.
     */
    public LFUCacheBuilder() {
        lfuCache = new LFUCache();
    }

    public LFUCacheBuilder setCapacity(int capacity) {
        lfuCache.setCapacity(capacity);
        return this;
    }

    public LFUCacheBuilder setServerName(String serverName) {
        lfuCache.setServerName(serverName);
        return this;
    }

    /**
     * Builds and returns the configured LFUCache instance.
     *
     * @return Configured LFUCache object.
     */
    public LFUCache build() {
        return lfuCache;
    }
}
