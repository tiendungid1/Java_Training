package rxn.algorithms.divideandconquer;

public class Test {
    public static void main(String[] args) {
        //System.out.println(sumArrayV1(new int[]{1, 2, 3, 4}));
        //System.out.println(sumArrayV2(new int[]{1, 2, 3, 4}));
        //System.out.println(countArrayElementsV1(new int[]{1, 2, 3, 4}));
        //System.out.println(countArrayElementsV2(new int[]{1, 2, 3, 4}));
    }

    private static int sumArrayV1(int[] arr) {
        // If array is empty, return 0
        if (arr.length == 0) {
            return 0;
        }
        // Otherwise, sum = first element + sum of the rest
        int[] tempArr = new int[arr.length - 1];
        System.arraycopy(arr, 1, tempArr, 0, tempArr.length);
        return arr[0] + sumArrayV1(tempArr);
    }

    private static int sumArrayV2(int[] arr) {
        return sumArrayV2Hidden(arr, 0);
    }

    private static int sumArrayV2Hidden(int[] arr, int index) {
        if (index == arr.length) {
            return 0;
        }
        return arr[index] + sumArrayV2Hidden(arr, index + 1);
    }

    private static int countArrayElementsV1(int[] arr) {
        // If array is empty, return 0
        if (arr.length == 0) {
            return 0;
        }
        // Otherwise, count = 1 + count of the rest
        int[] tempArr = new int[arr.length - 1];
        System.arraycopy(arr, 1, tempArr, 0, tempArr.length);
        return 1 + countArrayElementsV1(tempArr);
    }

    private static int countArrayElementsV2(int[] arr) {
        return countArrayElementsV2Hidden(arr, 0);
    }

    private static int countArrayElementsV2Hidden(int[] arr, int index) {
        if (index == arr.length) {
            return 0;
        }
        return 1 + countArrayElementsV2Hidden(arr, index + 1);
    }
}
