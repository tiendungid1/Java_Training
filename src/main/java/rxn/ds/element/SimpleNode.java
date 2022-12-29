package rxn.ds.element;

public class SimpleNode<E> {
  private SimpleNode<E> next;
  private E data;

  public SimpleNode(E data) {
    this.next = null;
    this.data = data;
  }

  public SimpleNode<E> getNext() {
    return next;
  }

  public void setNext(SimpleNode<E> next) {
    this.next = next;
  }

  public E getData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof SimpleNode<?> that)) {
      return false;
    }

    return next.equals(that.next) && data.equals(that.data);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + next.hashCode();
    result = 31 * result + data.hashCode();
    return result;
  }
}
