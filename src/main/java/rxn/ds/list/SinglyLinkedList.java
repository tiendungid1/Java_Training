package rxn.ds.list;

import rxn.ds.element.SimpleNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SinglyLinkedList<E> implements List<E> {

    private SimpleNode<E> head;
    private SimpleNode<E> tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }

        SimpleNode<E> temp = head;

        while (temp != null) {
            if (temp.data.equals(o)) {
                return true;
            }
            temp = temp.next;
        }

        return false;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) {
            return false;
        }

        // left is element before removing element, right is removing element
        SimpleNode<E> left = head;
        SimpleNode<E> right = head;

        while (right.next != null) {
            left = right;
            if (right.data.equals(o)) {
                break;
            }
            right = right.next;
        }

        // Only one node in the list and node's element == removing element
        // or
        // The first node in the list has element == removing element
        if (left.data == right.data) {
            Object r = removeFirst();
            return true;
        }

        // Only one node in the list and node's element != removing element
        if (!left.data.equals(right.data)) {
            return false;
        }

        // The data to be deleted does not exist
        if (!right.data.equals(o)) {
            return false;
        }

        // The others cases
        left.next = right.next;
        size--;

        return true;
    }

    @Override
    public boolean containsAll(Collection<E> c) {
        if (c.isEmpty()) {
            return false;
        }

        Set<E> set = new HashSet<>(c);
        SimpleNode<E> temp = head;
        while (temp.next != null) {
            if (!set.contains(temp.data)) {
                return false;
            }
            temp = temp.next;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }

        c.forEach(element -> {
            SimpleNode<E> newNode = new SimpleNode<>(element);
            tail.next = newNode;
            tail = newNode;
        });

        return true;
    }

    @Override
    public boolean removeAll(Collection<E> c) {
        if (c.isEmpty()) {
            return false;
        }

        boolean listHasChanged = false;

        for (E e : c) {
            if (remove(e)) {
                listHasChanged = true;
            }
        }

        return listHasChanged;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
    }

    @Override
    public E get(int index) {
        if (isEmpty()) {
            return null;
        }

        if (index < 0 || index > size - 1) {
            return null;
        }

        if (index == 0) {
            return head.data;
        }

        if (index == size - 1) {
            return tail.data;
        }

        SimpleNode<E> temp = head;

        for (int i = 0; i <= index; i++) {
            temp = temp.next;
        }

        return temp.data;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
        if (isEmpty()) {
            addLast(element);
            return;
        }

        if (index < 0 || index > size) {
            return;
        }

        if (index == 0) {
            addFirst(element);
            return;
        }

        if (index == size) {
            addLast(element);
            return;
        }

        SimpleNode<E> temp = head;

        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }

        SimpleNode<E> newNode = new SimpleNode<>(element);

        newNode.next = temp.next;
        temp.next = newNode;
        size++;
    }

    @Override
    public E remove(int index) {
        if (isEmpty()) {
            return null;
        }

        if (index < 0 || index > size - 1) {
            return null;
        }

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        SimpleNode<E> temp = head;

        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }

        // temp.next is the removing node
        SimpleNode<E> removingNode = temp.next;
        temp.next = removingNode.next;
        size--;

        return removingNode.data;
    }

    @Override
    public int indexOf(Object o) {
        SimpleNode<E> temp = head;

        for (int i = 0; i < size; i++) {
            if (temp.data.equals(o)) {
                return i;
            }
            temp = temp.next;
        }

        return -1;
    }

    private E removeLast() {
        SimpleNode<E> temp = head;

        for (int i = 0; i < size - 2; i++) {
            temp = temp.next;
        }

        E removingData = tail.data;

        tail = temp;
        temp.next = null;
        size--;

        return removingData;
    }

    private void addFirst(E e) {
        SimpleNode<E> newNode = new SimpleNode<>(e);

        if (isEmpty()) {
            tail = newNode;
            head = newNode; // avoid null pointer exception
        }

        newNode.next = head;
        head = newNode;
        size++;
    }

    private void addLast(E e) {
        SimpleNode<E> newNode = new SimpleNode<>(e);

        if (isEmpty()) {
            head = newNode;
            tail = newNode; // avoid null pointer exception
        }

        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private E removeFirst() {
        SimpleNode<E> temp = head;
        head = head.next;
        temp.next = null;
        size--;
        return temp.data;
    }
}
