package CacheImpl;

/**
 * Represents an item in the LFUCache with a key, value, and access frequency.
 */
public class LFUCacheItem {
    private String key;
    private Integer value;
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
    public LFUCacheItem(String key, Integer value) {
        this.key = key;
        this.value = value;
        this.frequency = 1;
    }

    /**
     * Creates and returns a clone of the current LFUCacheItem.
     *
     * @return A new LFUCacheItem object with the same key, value, and frequency.
     */
    public LFUCacheItem clone() {
        LFUCacheItem clonedCacheItem = new LFUCacheItem();
        clonedCacheItem.setKey(this.key);
        clonedCacheItem.setValue(this.value);
        clonedCacheItem.setFrequency(this.frequency);
        return clonedCacheItem;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setKey(String key_) {
        this.key = key_;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public int getFrequency() {
        return frequency;
    }
}
