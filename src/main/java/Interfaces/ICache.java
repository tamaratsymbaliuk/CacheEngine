package Interfaces;
/**
 * Interface defining methods for implementing cache behavior.
 */
public interface ICache <K,V> extends Iterable<K>{
    void put(K key, V value);

    V get(K key);

    void remove(K key);

    // optional methods to implement
    void clear();

    int getSize();

    boolean containsKey(K key);
}

