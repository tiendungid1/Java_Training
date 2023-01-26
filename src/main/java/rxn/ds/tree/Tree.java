package rxn.ds.tree;

import rxn.ds.element.BinaryTreeNode;

public interface Tree<E extends Comparable<E>> {
  boolean empty();

  int height();

  void insert(E data);

  boolean delete(E data) throws TreeIsEmptyException, NoSuchNodeException;

  E get(E data) throws TreeIsEmptyException, NoSuchNodeException;

  void printLevelOrder() throws TreeIsEmptyException;
}
