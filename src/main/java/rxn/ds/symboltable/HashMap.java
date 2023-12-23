package rxn.ds.symboltable;

import rxn.ds.tree.BinarySearchTree;
import rxn.ds.tree.Tree;

public class HashMap<K extends Comparable<K>, V> implements Map<K, V> {
    private static final int LOAD_FACTOR = 1;
    private int capacity = 13;
    private int size;
    private Object[] map;

    public HashMap() {
        map = new Object[capacity];
    }

    public HashMap(int capacity) {
        this.capacity = capacity;
        map = new Object[capacity];
    }

    @Override
    public boolean contains(K key) {
        return map[hash(key)] != null;
    }

    @Override
    public boolean put(K key, V value) {
        return put(map, key, value);
    }

    private boolean put(Object[] m, K key, V value) {
        if (value == null) {
            return false;
        }

        rehashing();

        int index = hash(key);

        // Empty slot: Add new tree to the hash table
        if (m[index] == null) {
            Tree<Entry<K, V>> tree = new BinarySearchTree<>();
            tree.add(new Entry<>(key, value));
            m[index] = tree;
            size++;
            return true;
        }

        Tree<Entry<K, V>> tree = treeFromRef(index);

        // Duplicate key: Overwrite old value
        if (tree.remove(new Entry<>(key, null))) {
            tree.add(new Entry<>(key, value));
            return true;
        }

        tree.add(new Entry<>(key, value));
        size++;
        return true;
    }

    @Override
    public boolean remove(K key) {
        int index = hash(key);

        if (map[index] == null) {
            return false;
        }

        return treeFromRef(index).remove(new Entry<>(key, null));
    }

    @Override
    public V get(K key) {
        int index = hash(key);

        if (map[index] == null) {
            return null;
        }

        final Entry<K, V> entry = new Entry<>(key, null);

        treeFromRef(index).forEach(Tree.TraversalWay.PREORDER, e -> {
            if (e.key.equals(key)) {
                entry.value = e.value;
            }
        });

        return entry.value;
    }

    @SuppressWarnings("unchecked")
    private Tree<Entry<K, V>> treeFromRef(int index) {
        return (Tree<Entry<K, V>>) map[index];
    }

    private int hash(K key) {
        return key.hashCode() % capacity;
    }

    private void rehashing() {
        if (size / capacity == LOAD_FACTOR) {
            capacity = capacity * 2;
            Object[] newMap = new Object[capacity];

            for (int i = 0; i < map.length; i++) {
                if (map[i] == null) {
                    continue;
                }
                treeFromRef(i).forEach(Tree.TraversalWay.PREORDER, e -> put(newMap, e.key, e.value));
            }

            map = newMap;
        }
    }
}
