package rxn.ds.queue;

public class Test {
    public static void main(String[] args) {
        testMonotonicQueue();
    }

    private static void testQueue() {
        Queue<Integer> queue = new CircularQueue<>(5);
        System.out.println("Empty: " + queue.isEmpty());
        System.out.println("Push: " + queue.push(1));
        System.out.println("Empty: " + queue.isEmpty());
        System.out.println("Push: " + queue.push(2));
        System.out.println("Push: " + queue.push(3));
        System.out.println("Push: " + queue.push(4));
        System.out.println("Push: " + queue.push(5));
        System.out.println("Pop: " + queue.pop());
        System.out.println("Pop: " + queue.pop());
        System.out.println("Size: " + queue.size());
        System.out.println("Push: " + queue.push(6));
        System.out.println("Push: " + queue.push(7));
        System.out.println("Push: " + queue.push(8));
        System.out.println("Front: " + queue.front());
        System.out.println("Rear: " + queue.rear());
        queue.print(true);
        System.out.println();
        System.out.println("Pop: " + queue.pop());
        System.out.println("Pop: " + queue.pop());
        System.out.println("Pop: " + queue.pop());
        System.out.println("Pop: " + queue.pop());
        System.out.println("Pop: " + queue.pop());
        System.out.println("Empty: " + queue.isEmpty());
    }

    private static void testDeque() {
        Deque<Integer> queue = new CircularDeque<>(5);
        System.out.println("Empty: " + queue.isEmpty());
        System.out.println("Push: " + queue.push(14));
        System.out.println("Empty: " + queue.isEmpty());
        System.out.println("Front: " + queue.front());
        System.out.println("Rear: " + queue.rear());
        System.out.println("Push: " + queue.push(20));
        System.out.println("Unshift: " + queue.unshift(50));
        System.out.println("Front: " + queue.front());
        System.out.println("Rear: " + queue.rear());
        queue.print(true);
        System.out.println();
        System.out.println("Push: " + queue.push(30));
        System.out.println("Unshift: " + queue.unshift(40));
        System.out.println("Front: " + queue.front());
        System.out.println("Rear: " + queue.rear());
        queue.print(true);
        System.out.println();
        System.out.println("Pop: " + queue.pop());
        System.out.println("Shift: " + queue.shift());
        System.out.println("Front: " + queue.front());
        System.out.println("Rear: " + queue.rear());
        queue.print(true);
        System.out.println();
    }

    private static void testMonotonicQueue() {
        Deque<Integer> queue = new MonotonicQueue<>(5);
        queue.push(1);
        queue.push(2);
        queue.push(4);
        queue.push(5);
        queue.push(6);
        queue.push(3);
        queue.print(true);
        System.out.println();
        queue.push(-1);
        queue.push(3);
        queue.push(3);
        queue.push(4);
        queue.push(5);
        queue.push(6);
        queue.push(7);
        queue.print(true);
    }
}
