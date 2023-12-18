package rxn.ds.element;

public class SimpleNode<E> {
    public final E data;
    public SimpleNode<E> next;

    public SimpleNode(E data) {
        this.next = null;
        this.data = data;
    }
}
