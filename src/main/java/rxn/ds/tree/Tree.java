package rxn.ds.tree;

import java.util.function.Consumer;

public interface Tree<E extends Comparable<E>> {
    boolean isEmpty();

    boolean isBalanced();

    int height();

    void add(E e);

    boolean remove(E e);

    boolean contains(E e);

    E min();

    E max();

    void forEach(TraversalWay way, Consumer<? super E> action);

    enum TraversalWay {
        LEVELORDER, INORDER, PREORDER, POSTORDER
    }
}
