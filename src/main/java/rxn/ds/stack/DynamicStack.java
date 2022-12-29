package rxn.ds.stack;

import rxn.ds.element.SimpleNode;

public class DynamicStack<E> extends Stack<E> {

  private SimpleNode<E> head;

  @Override
  public void push(E data) {
    SimpleNode<E> temp = new SimpleNode<>(data);
    temp.setNext(head);
    head = temp;
  }

  @Override
  public E pop() {
    if (empty()) throw new IndexOutOfBoundsException("Stack is empty");
    SimpleNode<E> temp = head;
    head = head.getNext();
    temp.setNext(null);
    return temp.getData();
  }

  @Override
  public boolean empty() {
    return head == null;
  }

  @Override
  public E peek() {
    return head.getData();
  }
}
