package rxn.ds.tree;

public class Test {
    public static void main(String[] args) {
        Tree<Integer> tree = new BinarySearchTree<>();
        tree.add(7);
        tree.add(4);
        tree.add(1);
        tree.add(6);
        tree.add(9);
        tree.print(Tree.TraversalWay.INORDER, true);
        System.out.println();
        System.out.println(tree.isBinary());
    }
}
