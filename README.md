# Cache Engine

## Overview

This is a cache program implemented using **creational design patterns** in Java. The cache supports operations like `put`, `get`, `remove`, and clearing of cache entries, with support for multiple cache strategies: **LRU (Least Recently Used)**, **FIFO (First-In, First-Out)**, and **LFU (Least Frequently Used)**. The program leverages design patterns like **Singleton**, **Factory Method**, **Builder**, and **Prototype** to provide flexibility and scalability in managing cache instances.

## Requirements
- **Cache key**: `String`
- **Cache value**: `Integer`
- **Cache operations**:
    - `put(String key, int value)`: Add or update a value in the cache.
    - `get(String key)`: Retrieve a value from the cache by key.
    - `remove(String key)`: Remove a value from the cache.
    - Optional operations:
        - `clear()`: Clear all cache entries.
        - `size()`: Get the current size of the cache.
        - `containsKey(String key)`: Check if a key exists in the cache.

## Features
- **FIFO Cache**: Removes the oldest cache entry when the capacity is full.
- **LRU Cache**: Removes the least recently used cache entry when the capacity is full.
- **LFU Cache**: Removes the least frequently used cache entry when the capacity is full.

## Design Patterns Used

### 1. **Factory Method Pattern**
The **Factory Method Pattern** is used to create different types of caches (`FIFO`, `LRU`, `LFU`). The `CacheFactory` class defines a static method `createCacheInstance`, which accepts a cache type and capacity, and returns an instance of the appropriate cache type.

```java
public class CacheFactory {
    public static ICache createCacheInstance(CacheTypeEnum type, int capacity) {
        switch (type) {
            case FIFO:
                return new FIFOCache(capacity);
            case LRU:
                return new LRUCache(capacity);
            case LFU:
                return new LFUCacheBuilder().setCapacity(capacity).build();
            default:
                throw new IllegalArgumentException("Unknown cache type");
        }
    }
}
```
### 2. **Builder Pattern**
The **Builder Pattern** is used to construct the LFUCache objects in a step-by-step manner, allowing for customization of properties like the cache's capacity. This pattern provides flexibility, ensuring that the cache is fully configured before use.
```java
public class LFUCacheBuilder {
    private LFUCache lfuCache;

    public LFUCacheBuilder() {
        this.lfuCache = new LFUCache();
    }

    public LFUCacheBuilder setCapacity(int capacity) {
        this.lfuCache.setCapacity(capacity);
        return this;
    }

    public LFUCache build() {
        return this.lfuCache;
    }
}
```
### 3. **Prototype Pattern**
The **Prototype Pattern** is used in the LFUCacheItem class, allowing cache items to be cloned rather than creating entirely new objects. This is useful for maintaining the original object’s state while performing operations like updating the frequency, ensuring each object is independent.
```java
public class LFUCacheItem {
    private String key;
    private int value;
    private int frequency;

    // other methods...

 
   // defining Prototype.
    public LFUCacheItem clone() {
        LFUCacheItem clonedCacheItem = new LFUCacheItem();
        clonedCacheItem.setKey(this.key);
        clonedCacheItem.setValue(this.value);
        clonedCacheItem.setFrequency(this.frequency);

        return clonedCacheItem;
    }

}
```

