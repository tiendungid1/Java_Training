package rxn.ds.stack;

import java.lang.reflect.Array;

public class StaticStack<E> extends Stack<E> {

  private E[] elements;

  private int topIndex = -1;

  private int capacity;

  public StaticStack(Class<E> c, int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Illegal Capacity: " + capacity);
    }

    @SuppressWarnings("unchecked")
    final E[] elements = (E[]) Array.newInstance(c, capacity);
    this.capacity = capacity;
    this.elements = elements;
  }

  @Override
  public void push(E data) {
    if (data == null) return;
    if (full()) return;
    elements[++topIndex] = data;
  }

  @Override
  public E pop() {
    if (empty()) throw new ArrayIndexOutOfBoundsException("Stack is empty");
    E element = elements[topIndex];
    elements[topIndex--] = null;
    return element;
  }

  @Override
  public boolean empty() {
    return topIndex == -1;
  }

  public boolean full() {
    return topIndex == capacity - 1;
  }

  @Override
  public E peek() {
    return elements[topIndex];
  }
}
