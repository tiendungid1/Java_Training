package rxn.stream.fi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Test {
  public static void main(String[] args) {
    UnaryOperator<String> unaryOperator = UnaryOperator.identity();
    System.out.println(unaryOperator.apply("abc"));

    List<Integer> list = Arrays.asList(1, 4, 12, 5, 1, 30, 22, 4);
    UnaryOperator<Integer> operator = n -> n * 2;
    List<Integer> listAfterTransform = transform(list, operator);
    listAfterTransform.forEach(n -> System.out.print(n + " "));
  }

  public static List<Integer> transform(List<Integer> list, UnaryOperator<Integer> operator) {
    List<Integer> result = new LinkedList<>();
    for (int num : list) {
      result.add(operator.apply(num));
    }
    return result;
  }
}
