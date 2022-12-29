package rxn.ds.tree;

import rxn.ds.element.BinaryTreeNode;

public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {

  private BinaryTreeNode<E> root;

  @Override
  public boolean empty() {
    return root == null;
  }

  @Override
  public int height() {
    return 0;
  }

  @Override
  public void insert(E data) {
    if (empty()) {
      root = new BinaryTreeNode<>(data);
      return;
    }
    insert(root, data);
  }

  private BinaryTreeNode<E> insert(BinaryTreeNode<E> node, E data) {
    if (node == null) {
      node = new BinaryTreeNode<>(data);
    } else if (data.compareTo(node.data) < 0) { // Case: Inserting data < node's data -> go left
      node.left = insert(node.left, data);
    } else if (data.compareTo(node.data) > 0) { // Case: Inserting data > node's data -> go right
      node.right = insert(node.right, data);
    } else { // Case: Data exists
      node.increaseCount();
    }
    return node;
  }

  @Override
  public boolean delete(E data) {
    return false;
  }

  @Override
  public E get(E data) {
    return null;
  }

  @Override
  public void levelOrderTraversal() {}
}
