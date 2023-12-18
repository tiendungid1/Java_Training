package rxn.ds.tree;

import rxn.ds.element.BinaryTreeNode;
import rxn.ds.queue.CircularDeque;
import rxn.ds.queue.Deque;

public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {

    private BinaryTreeNode<E> root;

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean isBinary() {
        return isBinary(root, true, true);
    }

    private boolean isBinary(BinaryTreeNode<E> node, boolean greaterThanLeft, boolean lesserThanRight) {
        if (node == null) {
            return true;
        }

        if (!(greaterThanLeft && lesserThanRight)) {
            return false;
        }

        isBinary(node.left, greaterThanLeft, lesserThanRight);
        greaterThanLeft = node.left == null || node.data.compareTo(node.left.data) > 0;

        isBinary(node.right, greaterThanLeft, lesserThanRight);
        lesserThanRight = node.right == null || node.data.compareTo(node.right.data) < 0;

        return greaterThanLeft && lesserThanRight;
    }

    @Override
    public boolean isBalanced() {
        return false;
    }

    @Override
    public int height() {
        if (isEmpty()) return -1;
        return height(root);
    }

    private int height(BinaryTreeNode<E> node) {
        if (node == null) return -1;
        int leftSubTreeHeight = height(node.left);
        int rightSubTreeHeight = height(node.right);
        return Math.max(leftSubTreeHeight, rightSubTreeHeight) + 1;
    }

    @Override
    public void add(E e) {
        if (isEmpty()) {
            root = new BinaryTreeNode<>(e);
            return;
        }
        add(root, e);
    }

    private BinaryTreeNode<E> add(BinaryTreeNode<E> node, E e) {
        if (node == null) {
            node = new BinaryTreeNode<>(e);
        } else if (e.compareTo(node.data) < 0) {
            node.left = add(node.left, e);
        } else if (e.compareTo(node.data) > 0) {
            node.right = add(node.right, e);
        } else {
            node.count++;
        }
        return node;
    }

    @Override
    public boolean remove(E e) {
        if (isEmpty()) {
            return false;
        }
        remove(root, e);
        return true;
    }

    private BinaryTreeNode<E> remove(BinaryTreeNode<E> node, E e) {
        if (node == null) {
            return null;
        }

        if (e.compareTo(node.data) < 0) {
            node.left = remove(node.left, e);
        } else if (e.compareTo(node.data) > 0) {
            node.right = remove(node.right, e);
        } else {
            // Duplicate node
            if (node.count > 1) {
                node.count--;
            }
            // Node has no child
            else if (node.left == null && node.right == null) {
                node = null;
            }
            // Node has one child to the left
            else if (node.right == null) {
                node = node.left;
            }
            // Node has one child to the right
            else if (node.left == null) {
                node = node.right;
            }
            // Node has two children
            // -> Find min of the right subtree and replace current node data with the min data
            else {
                E min = min(node.right);
                node.data = min;
                node.right = remove(node.right, min);
            }
        }

        return node;
    }

    @Override
    public boolean exists(E e) {
        return exists(root, e);
    }

    private boolean exists(BinaryTreeNode<E> node, E e) {
        if (node == null) {
            return false;
        }

        if (e.compareTo(node.data) < 0) {
            return exists(node.left, e);
        } else if (e.compareTo(node.data) > 0) {
            return exists(node.right, e);
        } else {
            return true;
        }
    }

    @Override
    public void print(TraversalWay way, boolean inline) {
        switch (way) {
            case LEVELORDER: {
                printLevelOrder(inline);
                break;
            }
            case PREORDER: {
                printPreorder(root, inline);
                break;
            }
            case INORDER: {
                printInorder(root, inline);
                break;
            }
            case POSTORDER: {
                printPostorder(root, inline);
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong traversal way");
            }
        }
    }


    private void printLevelOrder(boolean inline) {
        if (isEmpty()) {
            return;
        }

        Deque<BinaryTreeNode<E>> discoveredNodes = new CircularDeque<>(100);

        discoveredNodes.push(root);

        while (!discoveredNodes.isEmpty()) {
            BinaryTreeNode<E> current = discoveredNodes.pop();

            if (current.left != null) discoveredNodes.push(current.left);
            if (current.right != null) discoveredNodes.push(current.right);

            if (inline) {
                System.out.print(current.data);
                System.out.print(" ");
                continue;
            }

            System.out.println(current.data);
        }
    }

    private void printPreorder(BinaryTreeNode<E> node, boolean inline) {
        if (node == null) {
            return;
        }

        if (inline) {
            System.out.print(node.data);
            System.out.print(" ");
        } else {
            System.out.println(node.data);
        }

        printPreorder(node.left, inline);
        printPreorder(node.right, inline);
    }

    private void printInorder(BinaryTreeNode<E> node, boolean inline) {
        if (node == null) {
            return;
        }

        printInorder(node.left, inline);

        if (inline) {
            System.out.print(node.data);
            System.out.print(" ");
        } else {
            System.out.println(node.data);
        }

        printInorder(node.right, inline);
    }

    private void printPostorder(BinaryTreeNode<E> node, boolean inline) {
        if (node == null) {
            return;
        }

        printPostorder(node.left, inline);
        printPostorder(node.right, inline);

        if (inline) {
            System.out.print(node.data);
            System.out.print(" ");
        } else {
            System.out.println(node.data);
        }
    }

    public E min() {
        if (isEmpty()) {
            return null;
        }
        return min(root);
    }

    private E min(BinaryTreeNode<E> node) {
        if (node.left == null) return node.data;
        return min(node.left);
    }

    public E max() {
        if (isEmpty()) {
            return null;
        }
        return max(root);
    }

    private E max(BinaryTreeNode<E> node) {
        if (node.right == null) return node.data;
        return max(node.right);
    }
}
