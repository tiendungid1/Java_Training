package rxn.ds.element;

public class BinaryTreeNode<E extends Comparable<E>> {
    public BinaryTreeNode<E> left;
    public BinaryTreeNode<E> right;
    public E data;
    public int count = 1;

    public BinaryTreeNode(E data) {
        this.data = data;
    }
}
