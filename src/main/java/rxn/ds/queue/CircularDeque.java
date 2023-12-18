package rxn.ds.queue;

public class CircularDeque<E> extends CircularQueue<E> implements Deque<E> {
    public CircularDeque() {
        super();
    }

    public CircularDeque(int capacity) {
        super(capacity);
    }

    @Override
    public boolean unshift(E e) {
        if (size == capacity) {
            return false;
        }

        if (isEmpty()) {
            front = rear = 0;
        } else {
            front = (front + capacity - 1) % capacity;
        }

        queue[front] = e;
        size++;

        return true;
    }

    @Override
    public E shift() {
        if (isEmpty()) {
            return null;
        }

        Object e = queue[rear];
        queue[rear] = null;

        if (rear == front) {
            rear = front = -1;
        } else {
            rear = (rear + capacity - 1) % capacity;
        }

        size--;

        return elementData(e);
    }
}
