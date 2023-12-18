package rxn.ds.queue;

public interface Heap<E extends Comparable<E>> {
    /**
     * Check if heap is empty.
     *
     * @return true if empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Append new element to the end of the heap, skip maintaining the heap structure.
     *
     * @param e New element.
     */
    void quickAdd(E e);

    /**
     * Add new element to heap, while keeping the heap structure.
     *
     * @param e New element.
     */
    void add(E e);

    /**
     * Remove the root out of the heap.
     *
     * @return e The root element.
     */
    E extract();

    /**
     * Remove the element at the specified index out of the heap.
     *
     * @param index Index of element.
     * @return the removed element.
     */
    E remove(int index);

    /**
     * @return The root element.
     */
    E peek();

    /**
     * Check the priority of the element at specified index, and update position if needed.
     *
     * @param index Index of element.
     */
    void updatePosition(int index);

    /**
     * Heapify the array that hold the heap elements. Only needed if quickAdd(E e) has been used.
     * Also called automatically if calling sort().
     */
    void heapify();

    /**
     * Sort by ascending order if implementation is max heap,
     * descending order if implementation is min heap.
     */
    void sort();

    void print(boolean inline);
}
