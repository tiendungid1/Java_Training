package rxn.ds.symboltable;

import java.util.Objects;

public interface Map<K extends Comparable<K>, V> {
    boolean contains(K key);

    boolean put(K key, V value);

    boolean remove(K key);

    V get(K key);

    class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K, V>> {
        public K key;
        public V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public int compareTo(Entry<K, V> o) {
            return key.compareTo(o.key);
        }
    }
}
