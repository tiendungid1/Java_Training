package rxn;

public class Main {

  public static void main(String[] args) {
    printDuplicates(new int[] {1, 4, 6, 3, 4, 6, 5, 20, 20, 10});
  }

  public static void printDuplicates(int[] a) {
    int[] uniqueArr = new int[getMax(a) + 1];

    for (int val : a) {
      uniqueArr[val]++;
    }

    for (int i = 0; i < uniqueArr.length; i++) {
      if (uniqueArr[i] > 1) {
        System.out.print(i + " ");
      }
    }
  }

  public static int getMax(int[] a) {
    int max = a[0];
    for (int val : a) {
      if (max < val) max = val;
    }
    return max;
  }
}
