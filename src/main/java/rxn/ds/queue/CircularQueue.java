package rxn.ds.queue;

public class CircularQueue<E> implements Queue<E> {
    protected Object[] queue;
    protected int capacity = 10;
    protected int size;
    protected int front = -1;
    protected int rear = -1;

    public CircularQueue() {
        this.queue = new Object[capacity];
    }

    public CircularQueue(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        }
        this.queue = new Object[capacity];
    }

    @Override
    public boolean push(E e) {
        if (size == capacity) {
            return false;
        }

        if (isEmpty()) {
            front = rear = 0;
        } else {
            rear = (rear + 1) % capacity;
        }

        queue[rear] = e;
        size++;

        return true;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }

        Object e = queue[front];
        queue[front] = null;

        if (front == rear) {
            front = rear = -1;
        } else {
            front = (front + 1) % capacity;
        }

        size--;

        return elementData(e);
    }

    @Override
    public boolean isEmpty() {
        return front == -1 && rear == -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E front() {
        return elementData(queue[front]);
    }

    @Override
    public E rear() {
        return elementData(queue[rear]);
    }

    @Override
    public void print(boolean inline) {
        for (int i = 0; i < capacity; i++) {
            if (inline) {
                System.out.print(queue[i]);
                System.out.print(" ");
                continue;
            }
            System.out.println(queue[i]);
        }
    }

    @SuppressWarnings("unchecked")
    protected E elementData(Object data) {
        return (E) data;
    }
}
