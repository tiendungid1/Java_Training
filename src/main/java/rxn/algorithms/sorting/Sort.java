package rxn.algorithms.sorting;

import rxn.ds.list.ArrayList;
import rxn.ds.list.List;
import rxn.ds.queue.CircularDeque;
import rxn.ds.queue.Deque;

import java.util.Random;

public final class Sort {

    private static <E extends Comparable<E>> void selectionSort(List<E> list) {
        for (int i = 0; i < list.size(); i++) {
            int temp = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(list.get(temp)) < 0) {
                    temp = j;
                }
            }

            swap(list, i, temp);
        }
    }

    private static <E extends Comparable<E>> void bubbleSort(List<E> list) {
        boolean isSorted = true;

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    swap(list, j, j + 1);
                    isSorted = false;
                    continue;
                }
                isSorted = true;
            }

            if (isSorted) break;
        }
    }

    private static <E extends Comparable<E>> void insertionSort(List<E> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (list.get(j).compareTo(list.get(j - 1)) >= 0) {
                    break;
                }
                swap(list, j, j - 1);
            }
        }
    }

    private static <E extends Comparable<E>> void shellSort(List<E> list) {
        int gap = 1;

        // Calculate max gap for this array
        while (gap < list.size() / 3) {
            gap = 3 * gap + 1; // 1, 4, 13, 40, 121...
        }

        while (gap >= 1) {
            for (int i = gap; i < list.size(); i++) {
                for (int j = i; j >= gap; j = j - gap) {
                    if (list.get(j).compareTo(list.get(j - gap)) >= 0) {
                        break;
                    }
                    swap(list, j, j - gap);
                }
            }
            gap = gap / 3;
        }
    }

    private static <E extends Comparable<E>> void quickSort(List<E> list) {
        quickSortHidden(list, 0, list.size() - 1);
    }

    private static <E extends Comparable<E>> void quickSortHidden(List<E> list, int start, int end) {
        if (start >= end) return;
        int partitionIndex = randomizePartition(list, start, end);
        quickSortHidden(list, start, partitionIndex - 1);
        quickSortHidden(list, partitionIndex + 1, end);
    }

    private static <E extends Comparable<E>> int randomizePartition(List<E> list, int start, int end) {
        Random random = new Random();
        int randomPartitionIndex = random.nextInt(end - start) + start;
        swap(list, randomPartitionIndex, end);
        return partition(list, start, end);
    }

    private static <E extends Comparable<E>> int partition(List<E> list, int start, int end) {
        E pivot = list.get(end);
        int partitionIndex = start;

        for (int i = start; i < end; i++) {
            if (list.get(i).compareTo(pivot) <= 0) {
                swap(list, i, partitionIndex++);
            }
        }

        swap(list, partitionIndex, end);
        return partitionIndex;
    }

    private static <E extends Comparable<E>> void mergeSort(List<E> src) {
        if (src.size() < 2) return;

        int mid = src.size() / 2;
        List<E> left = new ArrayList<>(mid);
        List<E> right = new ArrayList<>(src.size() - mid);

        for (int i = 0; i < mid; i++) {
            left.set(i, src.get(i));
        }

        for (int i = mid; i < src.size(); i++) {
            right.set(i, src.get(i));
        }

        mergeSort(left);
        mergeSort(right);
        merge(src, left, right);
    }

    private static <E extends Comparable<E>> void merge(List<E> src, List<E> left, List<E> right) {
        int si = 0, li = 0, ri = 0;

        while (li < left.size() && ri < right.size()) {
            if (left.get(li).compareTo(right.get(ri)) <= 0) {
                src.set(si++, left.get(li++));
            } else {
                src.set(si++, right.get(ri++));
            }
        }

        while (li < left.size()) src.set(si++, left.get(li++));
        while (ri < right.size()) src.set(si++, right.get(ri++));
    }

    private static <E> void swap(List<E> list, int i, int j) {
        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private <E> void reverseOrder(List<E> list) {
        Deque<E> queue = new CircularDeque<>(list.size());
        list.forEach(queue::push);
        list.clear();
        while (!queue.isEmpty()) list.add(queue.shift());
    }

    public <E extends Comparable<E>> void apply(List<E> list) {
        apply(list, SortMethod.QUICK, SortDirection.ASCENDING);
    }

    public <E extends Comparable<E>> void apply(List<E> list, SortMethod method, SortDirection direction) {
        switch (method) {
            case SELECTION: {
                selectionSort(list);
                break;
            }
            case BUBBLE: {
                bubbleSort(list);
                break;
            }
            case INSERTION: {
                insertionSort(list);
                break;
            }
            case SHELL: {
                shellSort(list);
                break;
            }
            case MERGE: {
                mergeSort(list);
                break;
            }
            case QUICK: {
                quickSort(list);
                break;
            }
            case RADIX: {
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }

        if (direction.equals(SortDirection.DESCENDING)) {
            reverseOrder(list);
        }
    }

    public enum SortMethod {
        SELECTION, BUBBLE, INSERTION, SHELL, MERGE, QUICK, RADIX
    }

    public enum SortDirection {
        ASCENDING, DESCENDING
    }
}
