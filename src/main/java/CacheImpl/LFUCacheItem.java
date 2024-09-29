package CacheImpl;

/**
 * Represents an item in the LFUCache with a key, value, and access frequency.
 */
public class LFUCacheItem<K,V> {
    private K key;
    private V value;
    private int frequency;

    /**
     * Default constructor.
     */
    public LFUCacheItem() {}

    /**
     * Constructor to initialize an LFUCacheItem with a key and value.
     * Sets the initial frequency to 1.
     *
     * @param key   The key of the cache item.
     * @param value The value associated with the key.
     */
    public LFUCacheItem(K key, V value) {
        this.key = key;
        this.value = value;
        this.frequency = 1;
    }

    /**
     * Creates and returns a clone of the current LFUCacheItem.
     *
     * @return A new LFUCacheItem object with the same key, value, and frequency.
     */
    public LFUCacheItem<K,V> clone() {
        LFUCacheItem<K,V> clonedCacheItem = new LFUCacheItem<K,V>();
        clonedCacheItem.setKey(this.key);
        clonedCacheItem.setValue(this.value);
        clonedCacheItem.setFrequency(this.frequency);
        return clonedCacheItem;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key_) {
        this.key = key_;
    }
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
