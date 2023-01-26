package rxn.ds.tree;

import rxn.ds.element.BinaryTreeNode;
import rxn.ds.list.SinglyLinkedList;
import rxn.ds.queue.Queue;

public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {

  private BinaryTreeNode<E> root;

  @Override
  public boolean empty() {
    return root == null;
  }

  @Override
  public int height() {
    if (empty()) return -1;
    return height(root);
  }

  private int height(BinaryTreeNode<E> node) {
    if (node == null) return -1;
    int leftSubTreeHeight = height(node.left);
    int rightSubTreeHeight = height(node.right);
    return Math.max(leftSubTreeHeight, rightSubTreeHeight) + 1;
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
    } else if (data.compareTo(node.data) < 0) {
      node.left = insert(node.left, data);
    } else if (data.compareTo(node.data) > 0) {
      node.right = insert(node.right, data);
    } else {
      node.increaseCount();
    }
    return node;
  }

  @Override
  public boolean delete(E data) throws TreeIsEmptyException, NoSuchNodeException {
    if (empty()) throw new TreeIsEmptyException();
    delete(root, data);
    return true;
  }

  private BinaryTreeNode<E> delete(BinaryTreeNode<E> node, E data) throws NoSuchNodeException {
    if (node == null) throw new NoSuchNodeException();

    if (data.compareTo(node.data) < 0) {
      node.left = delete(node.left, data);
    } else if (data.compareTo(node.data) > 0) {
      node.right = delete(node.right, data);
    } else {
      //      Case: Duplicate node
      if (node.getCount() > 1) {
        node.decreaseCount();
      }
      //      Case: Node has no child
      else if (node.left == null && node.right == null) {
        node = null;
      }
      //      Case: Node has one child to the left
      else if (node.right == null) {
        BinaryTreeNode<E> temp = node;
        node = node.left;
        temp = null;
      }
      //      Case: Node has one child to the right
      else if (node.left == null) {
        BinaryTreeNode<E> temp = node;
        node = node.right;
        temp = null;
      }
      //      Case: Node has two children
      //      -> Find min of the right subtree and replace current node data with the min data
      else {
        E min = min(node.right);
        node.data = min;
        node.right = delete(node.right, min);
      }
    }

    return node;
  }

  @Override
  public E get(E data) throws TreeIsEmptyException, NoSuchNodeException {
    if (empty()) throw new TreeIsEmptyException();
    return get(root, data).data;
  }

  private BinaryTreeNode<E> get(BinaryTreeNode<E> node, E data) throws NoSuchNodeException {
    if (node == null) throw new NoSuchNodeException();

    if (data.compareTo(node.data) < 0) {
      return insert(node.left, data);
    } else if (data.compareTo(node.data) > 0) {
      return insert(node.right, data);
    } else {
      return node;
    }
  }

  @Override
  public void printLevelOrder() throws TreeIsEmptyException {
    if (empty()) throw new TreeIsEmptyException();
    Queue<BinaryTreeNode<E>> queue = new SinglyLinkedList<>();

    queue.addLast(root);

    while (!queue.empty()) {
      BinaryTreeNode<E> current = queue.removeFirst();
      System.out.print(current.data + " ");
      if (current.left != null) queue.addLast(current.left);
      if (current.right != null) queue.addLast(current.right);
    }
  }

  public E min() throws TreeIsEmptyException {
    if (empty()) throw new TreeIsEmptyException();
    return min(root);
  }

  private E min(BinaryTreeNode<E> node) {
    if (node.left == null) return node.data;
    return min(node.left);
  }

  public E max() throws TreeIsEmptyException {
    if (empty()) throw new TreeIsEmptyException();
    return max(root);
  }

  private E max(BinaryTreeNode<E> node) {
    if (node.right == null) return node.data;
    return max(node.right);
  }
}
