package rxn.ds.queue;

public interface Queue<E> {
    /**
     * Add a new element to the end of queue.
     *
     * @param e The element to add to queue.
     * @return true if the element has been added successfully, false otherwise.
     */
    boolean push(E e);

    /**
     * Consume an element at the beginning of queue.
     *
     * @return the removed element or null if queue is empty.
     */
    E pop();

    boolean isEmpty();

    int size();

    E front();

    E rear();

    void print(boolean inline);
}
