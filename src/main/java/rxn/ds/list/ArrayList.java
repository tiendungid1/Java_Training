package rxn.ds.list;

import java.util.*;

/**
 * Resizable-array implementation of the List interface.
 * <p></p>
 * Each ArrayList instance has a capacity. The capacity is the size of the array used to store the
 * elements in the list. It is always at least as large as the list size. As elements are added
 * to an ArrayList, its capacity grows automatically.
 * <p></p>
 *
 * @param <E> the type parameter.
 */
public class ArrayList<E> implements List<E> {
    private Object[] arr;
    private int capacity = 10;
    private int size;

    public ArrayList() {
        this.arr = new Object[capacity];
    }

    public ArrayList(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        }
        this.arr = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if ((o == null && arr[i] == null) || arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        expand();
        arr[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        // Array can contain null, but we can not remove null out of array
        Objects.requireNonNull(o);

        // Loop from first -> before the last element
        for (int i = 0; i < size - 1; i++) {
            if (arr[i] != null && arr[i].equals(o)) {
                shrink();
                System.arraycopy(arr, i + 1, arr, i, size - i);
                size--;
                return true;
            }
        }

        // The removing element is the final element of array
        if (arr[size - 1] != null && arr[size - 1].equals(o)) {
            shrink();
            arr[--size] = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<E> c) {
        if (c.isEmpty() || c.size() > size) {
            return false;
        }

        Map<Object, Boolean> map = new HashMap<>();
        c.forEach(el -> map.put(el, false));

        for (int i = 0; i < size; i++) {
            if (map.containsKey(arr[i])) {
                map.put(arr[i], true);
            }
        }

        for (Map.Entry<Object, Boolean> entry : map.entrySet()) {
            if (!entry.getValue()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        ensureCapacity(c.size());
        c.forEach(el -> arr[size++] = el);
        return true;
    }

    @Override
    public boolean removeAll(Collection<E> c) {
        if (c.isEmpty() || c.size() > size) {
            return false;
        }

        Set<Object> set = new HashSet<>(c);

        Object[] temp = new Object[capacity];
        int tempIndex = 0;

        for (int i = 0; i < size; i++) {
            if (!set.contains(arr[i])) {
                temp[tempIndex++] = arr[i];
            }
        }

        System.arraycopy(temp, 0, arr, 0, capacity);
        size = tempIndex;
        shrink();

        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            arr[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        return elementData(index);
    }

    @Override
    public E set(int index, E element) {
        Object temp = arr[index];
        arr[index] = element;
        return elementData(temp);
    }

    @Override
    public void add(int index, E element) {
        expand();

        if (index == size) {
            arr[size++] = element;
            return;
        }

        Object[] temp = new Object[size - index];
        int tempIndex = 0;

        for (int i = index; i < size; i++) {
            temp[tempIndex++] = arr[i];
        }

        arr[index] = element;
        System.arraycopy(temp, 0, arr, index + 1, temp.length);
        size++;
    }

    @Override
    public E remove(int index) {
        Object temp = arr[index];
        System.arraycopy(arr, index + 1, arr, index, size - index);
        size--;
        shrink();
        return elementData(temp);
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if ((o == null && arr[i] == null) || arr[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public void ensureCapacity(int colSize) {
        if (capacity - size >= colSize) {
            return;
        }

        int leftovers = capacity - size;
        int slotsNeeded = colSize - leftovers;
        capacity = capacity + slotsNeeded;
        Object[] newArr = new Object[capacity];

        System.arraycopy(arr, 0, newArr, 0, size);
        arr = newArr;
    }

    private void expand() {
        if (size == capacity) {
            Object[] newArr = new Object[capacity * 2];
            System.arraycopy(arr, 0, newArr, 0, size);
            arr = newArr;
            capacity = capacity * 2;
        }
    }


    private void shrink() {
        if (size == Math.floor((double) capacity / 4)) {
            Object[] newArr = new Object[capacity / 2];
            System.arraycopy(arr, 0, newArr, 0, size);
            arr = newArr;
            capacity = capacity / 2;
        }
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) arr[index];
    }

    @SuppressWarnings("unchecked")
    private E elementData(Object data) {
        return (E) data;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator<>(this);
    }

    private static class ListIterator<E> implements Iterator<E> {

        private final ArrayList<E> list;
        private int currentIndex;

        public ListIterator(ArrayList<E> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return list.get(currentIndex) != null;
        }

        @Override
        public E next() {
            return list.get(currentIndex++);
        }
    }
}
