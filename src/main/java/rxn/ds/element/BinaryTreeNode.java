package rxn.ds.element;

public class BinaryTreeNode<E extends Comparable<E>> {
  public BinaryTreeNode<E> left;
  public BinaryTreeNode<E> right;
  public E data;
  private int count = 1;

  public BinaryTreeNode(E data) {
    this.data = data;
  }

  public BinaryTreeNode<E> getLeft() {
    return left;
  }

  public void setLeft(BinaryTreeNode<E> left) {
    this.left = left;
  }

  public BinaryTreeNode<E> getRight() {
    return right;
  }

  public void setRight(BinaryTreeNode<E> right) {
    this.right = right;
  }

  public E getData() {
    return data;
  }

  public void setData(E data) {
    this.data = data;
  }

  public int getCount() {
    return count;
  }

  public void increaseCount() {
    count++;
  }
}
