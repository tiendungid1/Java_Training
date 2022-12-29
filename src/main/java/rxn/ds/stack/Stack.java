package rxn.ds.stack;

public abstract class Stack<E> {
  public abstract void push(E data);

  public abstract E pop();

  public abstract boolean empty();

  public abstract E peek();
}
