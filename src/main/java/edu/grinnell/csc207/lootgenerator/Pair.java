package edu.grinnell.csc207.lootgenerator;

/**
 * The pair class.
 */
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Constructor for the pair class, which is used frequently to decompose our
     * problem.
     * 
     * @param key   : the "left" or generally "key" value in a pair
     * @param value the "right" or generally "value" value in a pair
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * getKey : gets the key value in a pair
     * 
     * @return the key.
     */
    public K getKey() {
        return key;
    }

    /**
     * getValue : gets the key value in a pair
     * 
     * @return the value.
     */
    public V getValue() {
        return value;
    }
}
