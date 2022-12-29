//package rxn.codingQuestions.crackingTheCodingInterview.arraysAndStrings;
//
//public class Solution {
//  public static isUnique(String s) {
//
//  }
//
//  public static void findPairs(int[] arr) {
//    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//    int sum = 0, element = 0;
//
//    for (int i = 0; i < arr.length; i++) {
//      for (int j = i + 1; j < arr.length; j++) {
//        sum = arr[i] + arr[j];
//
//        if (map.containsKey(sum)) {
//          element = map.get(sum);
//          System.out.printf(
//              "Pairs: (%d, %d) (%d, %d) have sum = %d\n",
//              arr[i], arr[j], element, sum - element, sum);
//        } else {
//          map.put(sum, arr[i]);
//        }
//      }
//    }
//  }
//}
