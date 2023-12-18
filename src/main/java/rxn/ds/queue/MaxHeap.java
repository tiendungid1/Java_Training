package rxn.ds.queue;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {
    private Object[] heap;
    private int size;
    private int capacity = 10;

    public MaxHeap() {
        this.heap = new Object[capacity];
    }

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Object[capacity];
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return index * 2 + 1;
    }

    private int right(int index) {
        return index * 2 + 2;
    }

    private int lastNonLeafIndex() {
        return capacity / 2 - 1;
    }

    private void swap(int a, int b) {
        E temp = elementData(heap[b]);
        heap[b] = heap[a];
        heap[a] = temp;
    }

    private void siftUp(int index) {
        int parentIndex = parent(index);
        E parent = elementData(heap[parentIndex]);
        E current = elementData(heap[index]);

        if (parent.compareTo(current) >= 0) {
            return;
        }

        swap(parentIndex, index);
        siftUp(parentIndex);
    }

    private void siftDown(int index, int size) {
        int currentIndex = index;
        int leftIndex = left(currentIndex);
        int rightIndex = right(currentIndex);
        E left = elementData(heap[leftIndex]);
        E right = elementData(heap[rightIndex]);
        E current = elementData(heap[currentIndex]);

        if (leftIndex < size && left.compareTo(current) > 0) {
            currentIndex = leftIndex;
        }

        if (rightIndex < size && right.compareTo(current) > 0) {
            currentIndex = rightIndex;
        }

        if (currentIndex != index) {
            swap(currentIndex, index);
            siftDown(currentIndex, size);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void quickAdd(E e) {
        expand();
        heap[size++] = e;
    }

    @Override
    public void add(E e) {
        if (size == capacity) {
            expand();
        }
        heap[size] = e;
        siftUp(size);
        size++;
    }

    @Override
    public E extract() {
        if (isEmpty()) {
            return null;
        }

        E root = peek();

        // 1. Swap the root element with the last element
        swap(0, size - 1);
        // 2. Remove reference of element to allow Garbage Collector clean up
        heap[size--] = null;
        // 3. The current root element is of course not the largest element, so we need to sift it down
        siftDown(0, size);

        return root;
    }

    @Override
    public E remove(int index) {
        if (isEmpty() || (index < 0 || index >= size)) {
            return null;
        }

        E removedElement = elementData(heap[index]);
        // 1. Update the reference of removing element to point to the root element
        heap[index] = peek();
        // 2. With new value, we can use updatePosition to refactor the tree
        updatePosition(index);
        // 3. Remove the redundant root element
        extract();

        return removedElement;
    }

    @Override
    public E peek() {
        return isEmpty() ? null : elementData(heap[0]);
    }

    @Override
    public void updatePosition(int index) {
        if (isEmpty() || (index < 0 || index >= size)) {
            return;
        }

        E current = elementData(heap[index]);
        E parent = elementData(heap[parent(index)]);

        if (current.compareTo(parent) > 0) {
            siftUp(index);
            return;
        }

        E left = elementData(heap[left(index)]);
        E right = elementData(heap[right(index)]);

        if (current.compareTo(left) < 0 || current.compareTo(right) < 0) {
            siftDown(index, size);
        }
    }

    // Time complexity: Big O of n.log(n) - Theta of n
    @Override
    public void heapify() {
        for (int i = lastNonLeafIndex(); i >= 0; i--) {
            siftDown(i, size);
        }
    }

    // The siftDown function is key player here, and we need to give it a scope to work.
    // That scope is basically the size of the heap, but decrement by 1 with each loop.
    // 1. Build heap from the current array to ensure heap property.
    // 2. Swap root element with last element, now max/min element is at the end of array.
    // 3. Reduce the scope, and call siftDown.
    @Override
    public void sort() {
        if (isEmpty()) {
            return;
        }

        heapify();

        for (int i = size - 1; i > 0; i--) {
            swap(0, i);
            siftDown(0, i);
        }
    }

    @Override
    public void print(boolean inline) {
        for (int i = 0; i < capacity; i++) {
            if (inline) {
                System.out.print(heap[i]);
                System.out.print(" ");
                continue;
            }
            System.out.println(heap[i]);
        }
    }

    @SuppressWarnings("unchecked")
    private E elementData(Object data) {
        return (E) data;
    }

    private void expand() {
        if (size == capacity) {
            capacity = capacity * 2;
            Object[] newHeap = new Object[capacity];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }
    }

    private void shrink() {
        if (size == Math.floor((double) capacity / 4)) {
            capacity = capacity / 2;
            Object[] newHeap = new Object[capacity / 2];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }
    }
}
