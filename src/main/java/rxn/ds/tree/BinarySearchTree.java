package rxn.ds.tree;

import rxn.ds.element.BinaryTreeNode;
import rxn.ds.queue.CircularDeque;
import rxn.ds.queue.Deque;

import java.util.function.Consumer;

public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {

    private BinaryTreeNode<E> root;

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    public boolean isBST() {
        return isBST(root, null);
    }

    private boolean isBST(BinaryTreeNode<E> current, BinaryTreeNode<E> previous) {
        if (current == null) {
            return true;
        }

        if (!isBST(current.left, previous)) {
            return false;
        }

        if (previous != null && current.data.compareTo(previous.data) < 0) {
            return false;
        }

        previous = current;

        return isBST(current.right, previous);
    }

    @Override
    public boolean isBalanced() {
        if (isEmpty()) {
            return true;
        }
        return isBalanced(root);
    }

    private boolean isBalanced(BinaryTreeNode<E> node) {
        int leftSubtreeHeight = height(node.left);
        int rightSubtreeHeight = height(node.right);
        int heightDiff = Math.abs(leftSubtreeHeight - rightSubtreeHeight);

        return heightDiff <= 1 && isBalanced(node.left) && isBalanced(node.right);
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
        BinaryTreeNode<E> node = remove(root, e);
        return node != null;
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
    public boolean contains(E e) {
        return contains(root, e);
    }

    private boolean contains(BinaryTreeNode<E> node, E e) {
        if (node == null) {
            return false;
        }

        if (e.compareTo(node.data) < 0) {
            return contains(node.left, e);
        } else if (e.compareTo(node.data) > 0) {
            return contains(node.right, e);
        } else {
            return true;
        }
    }

    @Override
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

    @Override
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

    @Override
    public void forEach(TraversalWay way, Consumer<? super E> action) {
        switch (way) {
            case LEVELORDER: {
                levelorderProcessing(action);
                break;
            }
            case PREORDER: {
                preorderProcessing(root, action);
                break;
            }
            case INORDER: {
                inorderProcessing(root, action);
                break;
            }
            case POSTORDER: {
                postorderProcessing(root, action);
                break;
            }
            default: {
                throw new IllegalArgumentException("Something went wrong");
            }
        }
    }

    private void levelorderProcessing(Consumer<? super E> action) {
        if (isEmpty()) {
            return;
        }

        Deque<BinaryTreeNode<E>> discoveredNodes = new CircularDeque<>((int) Math.pow(2, height()));

        discoveredNodes.push(root);

        while (!discoveredNodes.isEmpty()) {
            BinaryTreeNode<E> current = discoveredNodes.pop();
            if (current.left != null) discoveredNodes.push(current.left);
            if (current.right != null) discoveredNodes.push(current.right);
            action.accept(current.data);
        }
    }

    private void preorderProcessing(BinaryTreeNode<E> node, Consumer<? super E> action) {
        if (node == null) {
            return;
        }
        action.accept(node.data);
        preorderProcessing(node.left, action);
        preorderProcessing(node.right, action);
    }

    private void inorderProcessing(BinaryTreeNode<E> node, Consumer<? super E> action) {
        if (node == null) {
            return;
        }
        inorderProcessing(node.left, action);
        action.accept(node.data);
        inorderProcessing(node.right, action);
    }

    private void postorderProcessing(BinaryTreeNode<E> node, Consumer<? super E> action) {
        if (node == null) {
            return;
        }
        postorderProcessing(node.left, action);
        postorderProcessing(node.right, action);
        action.accept(node.data);
    }
}
