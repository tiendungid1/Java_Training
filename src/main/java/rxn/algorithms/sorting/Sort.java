package rxn.algorithms.sorting;

import java.util.List;
import java.util.Random;

public final class Sort {
    private static void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private static boolean bubbleSwapAsc(List<Integer> list, int index) {
        if (list.get(index) > list.get(index + 1)) {
            swap(list, index, index + 1);
            return false;
        }
        return true;
    }

    private static boolean bubbleSwapDesc(List<Integer> list, int index) {
        if (list.get(index) < list.get(index + 1)) {
            swap(list, index, index + 1);
            return false;
        }
        return true;
    }

    public static void bubbleSort(List<Integer> list, int direction) {
        boolean isSorted;

        for (int i = 0; i < list.size(); i++) {
            isSorted = true;

            for (int j = 0; j < list.size() - i - 1; j++) {
                if (direction == 1) {
                    isSorted = bubbleSwapAsc(list, j);
                    continue;
                }
                isSorted = bubbleSwapDesc(list, j);
            }

            if (isSorted) break;
        }
    }

    public static void quickSort(List<Integer> list, int direction) {
        quickSortHidden(list, 0, list.size() - 1, direction);
    }

    private static void quickSortHidden(List<Integer> list, int start, int end, int direction) {
        if (start >= end) return;
        int partitionIndex = randomizePartition(list, start, end, direction);
        quickSortHidden(list, start, partitionIndex - 1, direction);
        quickSortHidden(list, partitionIndex + 1, end, direction);
    }

    private static int partition(List<Integer> list, int start, int end, int direction) {
        int pivot = list.get(end);
        int partitionIndex = start;

        for (int i = start; i < end; i++) {
            if (direction == 1) { // Sort asc
                if (list.get(i) <= pivot) swap(list, i, partitionIndex++);
                continue;
            }

            // Sort desc
            if (list.get(i) >= pivot) swap(list, i, partitionIndex++);
        }

        swap(list, partitionIndex, end);
        return partitionIndex;
    }

    private static int randomizePartition(List<Integer> list, int start, int end, int direction) {
        Random random = new Random();
        int randomPartitionIndex = random.nextInt(end - start) + start;
        swap(list, randomPartitionIndex, end);
        return partition(list, start, end, direction);
    }

    private static void merge(int[] a, int[] l, int[] r, int leftArrSize, int rightArrSize) {
        int ai = 0, li = 0, ri = 0;

        while (li < leftArrSize && ri < rightArrSize) {
            if (l[li] <= r[ri]) {
                a[ai++] = l[li++];
            } else {
                a[ai++] = r[ri++];
            }
        }

        while (li < leftArrSize) a[ai++] = l[li++];
        while (ri < rightArrSize) a[ai++] = r[ri++];
    }

    public static void mergeSort(int[] a, int arrSize) {
        if (arrSize < 2) return;

        int mid = arrSize / 2;
        int[] l = new int[mid], r = new int[arrSize - mid];

      System.arraycopy(a, 0, l, 0, mid);
      if (arrSize - mid >= 0) System.arraycopy(a, mid, r, mid - mid, arrSize - mid);

        mergeSort(l, mid);
        mergeSort(r, arrSize - mid);
        merge(a, l, r, mid, arrSize - mid);
    }
}
