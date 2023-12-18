package rxn.ds.list;

import java.util.Collection;

/**
 * The interface List.
 *
 * @param <E> the type parameter.
 */
public interface List<E> {
    /**
     * If this list contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of elements in this list.
     */
    int size();

    /**
     * @return true if this list contains no elements.
     */
    boolean isEmpty();

    /**
     * Returns true if and only if this list contains at least one element e such that
     * (o == null ? e == null : o.equals(e)).
     *
     * @param o The compare element.
     * @return true if this list contains the specified element.
     */
    boolean contains(Object o);

    /**
     * @param e The element.
     * @return Appends the specified element to the end of this list.
     */
    boolean add(E e);

    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     * If this list does not contain the element, it is unchanged.
     *
     * @param o The removing element.
     * @return true if this list contained the specified element (or equivalently, if this list changed as a result of the call).
     */
    boolean remove(Object o);

    /**
     * @param c collection containing elements to be checked for containment in this list.
     * @return true if this list contains all the elements of the specified collection.
     */
    boolean containsAll(Collection<E> c);

    /**
     * @param c collection containing elements to be added in this list.
     * @return true if this list changed as a result of the call.
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Removes from this list all of its elements that are contained in the specified collection.
     *
     * @param c collection containing elements to be removed from this list.
     * @return true if this list changed as a result of the call.
     */
    boolean removeAll(Collection<E> c);

    /**
     * Removes all the elements from this list. The list will be empty after this call returns.
     */
    void clear();

    /**
     * Get e.
     *
     * @param index Position of element.
     * @return the element at the specified position in this list.
     */
    E get(int index);

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index   Position of element.
     * @param element The element.
     * @return the element previously at the specified position.
     */
    E set(int index, E element);

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent elements to the right.
     *
     * @param index   Position of element.
     * @param element The element.
     */
    void add(int index, E element);

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     *
     * @param index Position of element.
     * @return the element that was removed from the list.
     */
    E remove(int index);

    /**
     * @param o The element.
     * @return the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
     */
    int indexOf(Object o);
}
