package rxn.ds.queue;

public interface Queue<E> {
  void addLast(E data);

  E removeFirst();

  boolean empty();

  int size();

  E front();

  E rear();
}
