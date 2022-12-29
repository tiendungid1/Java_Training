package rxn.ds.tree;

import rxn.ds.element.BinaryTreeNode;

public interface Tree<E extends Comparable<E>> {
  boolean empty();

  int height();

  void insert(E data);

  boolean delete(E data);

  E get(E data);

  void levelOrderTraversal();
}
