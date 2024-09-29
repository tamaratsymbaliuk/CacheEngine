package CacheImpl;

import Enums.CacheTypeEnum;
import Interfaces.ICache;

import java.text.MessageFormat;

/**
 * Main class to test the various cache implementations: LFU, LRU, and FIFO.
 * It contains test methods to validate the behavior of each cache type.
 */
public class Main {
    /**
     * Entry point of the program. Runs test cases for different cache types.
     *
     * @param args Command-line arguments (not used).
     * @throws Exception if any error occurs during execution.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        //testLFUCache();
        //testFIFOCache();
        //testLRUCache();
        //testFIFOMeasureTimeCacheDecorator();
        //testLegacyCacheAdapter();
        testFIFOCacheIterator();
        testGenerics();

    }

    /**
     * Tests the LRU (Least Recently Used) cache implementation.
     * Demonstrates cache operations like put, get, and eviction of the least recently used element.
     */
    private static void testLRUCache() {
        ICache<String, Integer> cache = (new CacheFactory<String,Integer>()).createCacheInstance(CacheTypeEnum.LRU, 3);

        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);
        cache.get("key1"); // Access key1, it becomes the most recently used
        cache.put("key4", 10); // This should evict key2, the least recently used

        System.out.println(cache.get("key1")); // Expected: 1
        System.out.println(cache.get("key3")); // Expected: 3
        System.out.println(cache.get("key4")); // Expected: 10

        // Expect an error message for key2 and return 0 since key2 was evicted.
        System.out.println(cache.get("key2"));
    }

    /**
     * Tests the LFU (Least Frequently Used) cache implementation.
     * Demonstrates cache operations including put, get, and eviction of the least frequently used element.
     */
    private static void testLFUCache() {
        ICache<String, Integer> cache = (new CacheFactory<String,Integer>()).createCacheInstance(CacheTypeEnum.LFU, 3);

        cache.put("key1", 1); // key1:1, freq 1
        cache.put("key1", 6); // key1:6, freq 2
        cache.put("key2", 2); // key1:6, key2:2
        cache.put("key3", 3); // cache: key1:6, key2:2, key3:3
        cache.get("key3"); // get key3

        // Until this line, frequencies of keys are:
        // [key1: 2, key2: 1, key3: 2], where the 1st element is key, the 2nd is frequency.
        // The next line removes the key with least frequency, it is key2 with frequency = 1.
        cache.put("key4", 10); // cache: key1:6, key3:3, key4:10

        System.out.println(cache.get("key1")); // Expected: 6
        System.out.println(cache.get("key3")); // Expected: 3
        System.out.println(cache.get("key4")); // Expected: 10

        // Expect an error message for key2 and return 0 since key2 was evicted.
        System.out.println(cache.get("key2"));
    }

    /**
     * Tests the FIFO (First In, First Out) cache implementation.
     * Demonstrates cache operations including put, get, and eviction of the earliest added element.
     */
    private static void testFIFOCache() {
        ICache<String, Integer> cache = (new CacheFactory<String,Integer>()).createCacheInstance(CacheTypeEnum.FIFO, 3);
        cache.put("key1", 1); // [key1:1, ]
        cache.put("key2", 2); // [key1:1, key2:2, ]
        cache.put("key3", 3); // [key1:1, key2:2, key3:3, ]
        cache.put("key1", 6); // [key2:2, key3:3, key1:6, ]
        cache.put("key2", 5); // [key3:3, key1:6, key2:5, ] cap = 3

        // Until this line, the order of keys in the FIFO cache are:
        // [key3: 3, key1: 6, key2: 5], where the 1st element is key, the 2nd is value.
        // The next line removes the key that was updated/added earlier than other keys -> it is key3.
        cache.put("key4", 10); // remove key3 as it modified earlier than key1 and key2, [key1:6, key2:5, key4:10]

        System.out.println(cache.get("key1")); // Expected: 6
        System.out.println(cache.get("key2")); // Expected: 5
        System.out.println(cache.get("key4")); // Expected: 10

        // Expect an error message for key3 and return 0 since key3 was evicted.
        System.out.println(cache.get("key3"));
    }

    private static void testFIFOMeasureTimeCacheDecorator(){
        CacheDecorator<String, Integer> decorator = new CacheTimeMeasureDecorator<String, Integer>((new CacheFactory<String,Integer>()).createCacheInstance(CacheTypeEnum.FIFO, 3));
        decorator.put("key1", 1); // [key1:1, ]
        decorator.put("key2", 2); // [key1:1, key2:2, ]

        System.out.println(MessageFormat.format("Key: {0}", decorator.get("key1")));
    }

    private static void testLegacyCacheAdapter(){
        ICache<String, Integer> cache = new LegacyCacheAdapter<String, Integer>(3);
        cache.put("key1", 1); // [key1:1, ]
        cache.put("key2", 2); // [key1:1, key2:2, ]
        cache.put("key3", 3); // [key1:1, key2:2, key3:3 ]
        cache.put("key4", 4); // [key1:1, key2:2, key3:3 ]
        cache.put("key3", 4); // [key1:1, key2:2, key3:4 ]
        cache.remove("key1");
        cache.put("key4", 4); // [key1:1, key2:2, key3:3 ]
    }
    private static void testFIFOCacheIterator(){
        ICache<String,Integer> cache = (new CacheFactory<String,Integer>()).createCacheInstance(CacheTypeEnum.FIFO, 3);
        cache.put("key1", 1); // [key1:1, ]
        cache.put("key2", 2); // [key1:1, key2:2, ]
        cache.put("key3", 3); // [key1:1, key2:2, key3:3, ]

        System.out.println("Cache keys are:");
        for (String key : cache) {
            System.out.println(key);
        }
    }
    private static void testGenerics() {
        ICache<Double,String> cache =  (new CacheFactory<Double,String>()).createCacheInstance(CacheTypeEnum.FIFO, 3);

        cache.put(1.01, "minimum"); // [key1:1, ]
        cache.put(1.05, "average"); // [key1:1, key2:2, ]
        cache.put(1.10, "maximum"); // [key1:1, key2:2, key3:3, ]

        System.out.println("Cache keys are:");
        for (Double key : cache) {
            System.out.println(key);
        }
    }

    // cache with key is string, value is string
    // cache with key is integer, value is double
    private static void testGenerics1() {
        ICache<Integer,Double> cache =  (new CacheFactory<Integer,Double>()).createCacheInstance(CacheTypeEnum.FIFO, 3);
    }

}

