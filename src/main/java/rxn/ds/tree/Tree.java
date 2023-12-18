package rxn.ds.tree;

public interface Tree<E extends Comparable<E>> {
    boolean isEmpty();

    boolean isBinary();

    boolean isBalanced();

    int height();

    void add(E e);

    boolean remove(E e);

    boolean exists(E e);

    void print(TraversalWay way, boolean inline);

    enum TraversalWay {
        LEVELORDER, INORDER, PREORDER, POSTORDER
    }
}
