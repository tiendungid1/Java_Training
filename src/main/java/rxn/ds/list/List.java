package rxn.ds.list;

public interface List<E> {
  int size();

  boolean empty();

  boolean contains(E data);

  E remove(E data) throws NoSuchFieldException;

  E remove(int index);

  E get(int index);
}
