package rxn.ds.queue;

public class MonotonicQueue<E extends Comparable<E>> extends CircularDeque<E> implements Deque<E> {
    private boolean increasing = true;

    public MonotonicQueue() {
        super();
    }

    public MonotonicQueue(int capacity) {
        super(capacity);
    }

    public MonotonicQueue(boolean increasing) {
        super();
        this.increasing = increasing;
    }

    public MonotonicQueue(int capacity, boolean increasing) {
        super(capacity);
        this.increasing = increasing;
    }

    @Override
    public boolean unshift(E e) {
        while (!isEmpty() && (increasing ? front().compareTo(e) <= 0 : front().compareTo(e) >= 0)) {
            pop();
        }

        return super.unshift(e);
    }

    @Override
    public boolean push(E e) {
        while (!isEmpty() && (increasing ? rear().compareTo(e) >= 0 : rear().compareTo(e) <= 0)) {
            shift();
        }

        return super.push(e);
    }
}
