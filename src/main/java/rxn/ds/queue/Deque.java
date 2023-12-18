package rxn.ds.queue;

public interface Deque<E> extends Queue<E> {
    /**
     * Add an element to the beginning of the queue.
     *
     * @param e The element to add to queue.
     * @return true if the element has been added successfully, false otherwise.
     */
    boolean unshift(E e);

    /**
     * Remove an element at the end of the queue.
     *
     * @return the removed element or null if queue is empty.
     */
    E shift();
}
