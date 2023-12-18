package rxn.codingQuestions.arraysAndStrings;

import java.util.LinkedList;
import java.util.List;

public class Solution {
  public static void moveZeroesToLast(int[] arr) {
    int start = 0, end = arr.length - 1;

    while (start != end) {
      if (arr[start] == 0 && arr[end] == 0) {
        end--;
        continue;
      }

      if (arr[start] == 0 && arr[end] != 0) {
        swap(arr, start, end);
      }

      if (arr[start] != 0) {
        start++;
      }

      if (arr[end] == 0) {
        end--;
      }
    }
  }

  public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public static void findSubArrayEqualSum(int[] arr, int sum) {
    int n = arr.length;

    int currentSum = 0;
    int start = 0;

    for (int i = 0; i < n; i++) {
      while (currentSum > sum && start < i) {
        currentSum -= arr[start++];
      }

      if (currentSum != 0 && currentSum == sum) {
        System.out.printf("There is a sub array equal %d from position '%d' to '%d'",
                          sum,
                          start,
                          i - 1
        );
        return;
      }

      currentSum += arr[i];
    }

    System.out.printf("There is no sub array equal %d", sum);
  }

  public static List<Integer> findLeaders(List<Integer> list) {
    int n = list.size();

    if (n <= 1) {
      return list;
    }

    List<Integer> result = new LinkedList<>();
    result.add(list.get(n - 1));

    int max = list.get(n - 1);

    for (int i = n - 2; i >= 0; i--) {
      if (list.get(i) > max) {
        max = list.get(i);
        result.add(max);
      }
    }

    return result;
  }

  public static boolean isUnique(String s) {
    // We don't accept the empty case
    if (s.length() == 0) {
      return false;
    }

    // Obviously unique
    if (s.length() == 1) {
      return true;
    }

    // Optimize case to avoid creating array
    if (s.length() == 2) {
      return s.charAt(0) != s.charAt(1);
    }

    // Create the array to store the count of character in string
    // Length 26 because from a - z, there are 26 characters
    int[] frequency = new int[26];

    // This declaration is not necessary, I wrote this just to use for-each loop
    char[] chars = s.toCharArray();

    // Loop through the string and store the count in the frequency array
    for (char c : chars) {
      // c - 'a' used to calculate the index, the expression will convert both variable to unicode code,
      // so if c is 'a' -> 'a' - 'a' will be 0, 'b' - 'a' will be 1... and 'z' - 'a' will be 25
      frequency[c - 'a']++;
    }

    // Loop through frequency and check if count > 1, that mean the string is not unique
    for (int count : frequency) {
      if (count > 1) {
        return false;
      }
    }

    // String is unique now
    return true;
  }

  public static boolean oneEditAtMost(String s1, String s2) {
    if (s1 == null || s2 == null) {
      return false;
    }

    // The length() is a function but not a getter, so each time call length(),
    // some code must run to retrieve the length. So we should cache them if we use them regularly.
    int s1Len = s1.length();
    int s2Len = s2.length();

    // We do not accept the case when one of string is empty
    if (s1Len == 0 || s2Len == 0) {
      return false;
    }

    // If difference between 2 length is more than one,
    // it means the strings have more than 1 edit
    if (Math.abs(s1Len - s2Len) > 1) {
      return false;
    }

    // A little optimization
    if (s1Len == 1 && s2Len == 1 && s1.equals(s2)) {
      return true;
    }

    // Determine which one is longer, this is important step
    String longer = s1;
    String shorter = s2;

    if (longer.length() < shorter.length()) {
      String temp = longer;
      longer = shorter;
      shorter = temp;
    }

    int longerLen = longer.length(), shorterLen = shorter.length();
    int li = 0, si = 0, editCount = 0; // li is longer index, si is shorter index

    for (int i = 0; i < shorterLen; i++) {
      // More than one edit, so false
      if (editCount > 1) {
        return false;
      }

      // When the character is different, it means an edit was found -> increase the count
      if (longer.charAt(li) != shorter.charAt(si)) {
        editCount++;
        li++; // Increase the longer index only, for the case if edit type is 'add a character'

        // If edit type is not 'add', then string length must be the same
        // So we need to increase the shorter index too
        if (longerLen == shorterLen) {
          si++;
        }

        continue;
      }

      // If edit was not found, increase both index to continue the loop
      li++;
      si++;
    }

    return editCount <= 1;
  }

  public static void toWaveForm(int[] arr) {
    // The wave form need at least 1 up and 1 down, so array with length < 3 is invalid input
    if (arr.length < 3) {
      return;
    }

    // The idea is if we make sure that all even positioned (at index 0, 2, 4, ..)
    // elements are greater than their adjacent odd elements, we don’t need to worry about
    // oddly positioned elements.

    // We loop to len - 1 because the last element does not need to be sort
    // Also it will help us to avoid index out of bound exception
    for (int i = 0; i < arr.length - 1; i += 2) {
      if (i == 0 && arr[i] < arr[i + 1]) {
        swap(arr, i, i + 1);
        continue;
      }

      if (arr[i] < arr[i - 1]) {
        swap(arr, i, i - 1);
      }

      if (arr[i] < arr[i + 1]) {
        swap(arr, i, i + 1);
      }
    }
  }
}
