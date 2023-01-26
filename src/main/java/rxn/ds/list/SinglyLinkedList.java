package rxn.ds.list;

import rxn.ds.element.SimpleNode;
import rxn.ds.queue.Queue;

public class SinglyLinkedList<E> implements List<E>, Queue<E> {

  private SimpleNode<E> head;
  private SimpleNode<E> tail;
  private int size;

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean empty() {
    return size == 0;
  }

  @Override
  public boolean contains(E data) {
    if (empty()) return false;

    SimpleNode<E> temp = head;

    while (temp != null) {
      if (temp.getData() == data) return true;
      temp = temp.getNext();
    }

    return false;
  }

  @Override
  public E get(int index) {
    if (empty()) return null;

    if (index < 0 || index > size - 1) {
      throw new IndexOutOfBoundsException(index);
    }

    if (index == 0) return head.getData();

    if (index == size - 1) return tail.getData();

    SimpleNode<E> temp = head;

    for (int i = 0; i <= index; i++) {
      temp = temp.getNext();
    }

    return temp.getData();
  }

  public void addFirst(E data) {
    SimpleNode<E> temp = new SimpleNode<>(data);

    if (empty()) {
      tail = temp;
      head = temp; // avoid null pointer exception
    }

    temp.setNext(head);
    head = temp;
    size++;
  }

  @Override
  public void addLast(E data) {
    SimpleNode<E> temp = new SimpleNode<>(data);

    if (empty()) {
      head = temp;
      tail = temp; // avoid null pointer exception
    }

    tail.setNext(temp);
    tail = temp;
    size++;
  }

  private void insert(E data, int index) {
    SimpleNode<E> temp = head;

    for (int i = 0; i < index - 1; i++) {
      temp = temp.getNext();
    }

    SimpleNode<E> node = new SimpleNode<>(data);

    node.setNext(temp.getNext());
    temp.setNext(node);
    size++;
  }

  public void add(E data, int index) {
    if (empty()) {
      addLast(data);
      return;
    }

    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(index);
    }

    if (index == 0) {
      addFirst(data);
      return;
    }

    if (index == size) {
      addLast(data);
      return;
    }

    insert(data, index);
  }

  public E removeFirst() {
    if (empty()) throw new IllegalStateException("List is empty");

    SimpleNode<E> temp = head;
    head = head.getNext();
    temp.setNext(null);
    size--;

    return temp.getData();
  }

  public E removeLast() {
    if (empty()) throw new IllegalStateException("List is empty");

    SimpleNode<E> temp = head;

    for (int i = 0; i < size - 2; i++) {
      temp = temp.getNext();
    }

    E removingData = tail.getData();

    tail = temp;
    temp.setNext(null);
    size--;

    return removingData;
  }

  @Override
  public E front() {
    return head.getData();
  }

  @Override
  public E rear() {
    return tail.getData();
  }

  @Override
  public E remove(E data) throws NoSuchFieldException {
    if (empty()) throw new IllegalStateException("List is empty");

    SimpleNode<E> left = head;
    SimpleNode<E> right = head;

    while (right.getNext() != null) {
      left = right;
      if (right.getData() == data) break;
      right = right.getNext();
    }

    // Case: Only one SimpleNode in the list and that SimpleNode's data == removing data
    // Case: The first SimpleNode in the list has data == removing data
    if (left.getData() == right.getData()) {
      return removeFirst();
    }

    // Case: Only one SimpleNode in the list and that SimpleNode's data != removing data
    if (left.getData() != right.getData())
      throw new NoSuchFieldException("The data to be deleted does not exist");

    // Case: The data to be deleted does not exist
    if (right.getData() != data)
      throw new NoSuchFieldException("The data to be deleted does not exist");

    // The others cases
    left.setNext(right.getNext());
    size--;

    return right.getData();
  }

  @Override
  public E remove(int index) {
    if (empty()) throw new IllegalStateException("List is empty");

    if (index < 0 || index > size - 1) {
      throw new IndexOutOfBoundsException(index);
    }

    if (index == 0) return removeFirst();

    if (index == size - 1) return removeLast();

    SimpleNode<E> temp = head;

    for (int i = 0; i < index - 1; i++) {
      temp = temp.getNext();
    }

    // temp.getNext() is the removing SimpleNode
    SimpleNode<E> removingNode = temp.getNext();
    temp.setNext(removingNode.getNext());
    size--;

    return removingNode.getData();
  }
}
