package rxn.utils;

import java.util.Stack;

/**
 * Used to evaluate mathematics infix, prefix, postfix expressions, coupled with some other
 * features.
 *
 * <p>1. Can only evaluate expression with basic operator ( +, -, *, / ).
 *
 * <p>2. Only accept integer digits.
 */
public final class MathExpUtil {
  private static final double INVALID_NUMBER_RESULT = Double.NEGATIVE_INFINITY;

  public static String infixToPostfix(String exp, boolean toPostfix) {
    /*  POSTFIX
     *     Infix      ->      Postfix
     *      2+3                 23+
     *      p-q                 pq-
     *     a+b*c               abc*+
     *   a*b+c*d-e           ab*cd*+e-
     * */
    exp = checkValidExpAndTrimWhiteSpace(exp);
    StringBuilder sb = new StringBuilder();
    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < exp.length(); i++) {
      int charCode = exp.codePointAt(i);

      if (i == exp.length() - 1) {
        if (!isClosingParenthesis(charCode)) {
          sb.append(exp.charAt(i));
          sb.append(" ");
        }
        break;
      }

      int nextCharCode = exp.codePointAt(i + 1);

      if (Character.isDigit(charCode)) {
        if (Character.isDigit(nextCharCode)) {
          sb.append(exp.charAt(i));
          continue;
        } else {
          sb.append(exp.charAt(i));
          sb.append(" ");
          continue;
        }
      }

      if (isBasicOperator(charCode)) {
        while (!stack.empty()
            && !isOpeningParenthesis(stack.peek())
            && hasHigherPrecedence(stack.peek(), charCode, toPostfix)) {
          int operatorCode = stack.pop();
          sb.append((char) operatorCode);
          sb.append(" ");
        }
        stack.push(charCode);
        continue;
      }

      if (isOpeningParenthesis(charCode)) {
        stack.push(charCode);
        continue;
      }

      if (isClosingParenthesis(charCode)) {
        while (!stack.empty() && !isOpeningParenthesis(stack.peek())) {
          int operatorCode = stack.pop();
          sb.append((char) operatorCode);
        }
        stack.pop();
        sb.append(" ");
      }
    }

    while (!stack.empty()) {
      int charCode = stack.pop();
      if (isOpeningParenthesis(charCode)) continue;
      sb.append((char) charCode);
      sb.append(" ");
    }

    sb.setLength(sb.length() - 1);

    return sb.toString();
  }

  public static String infixToPrefix(String exp) {
    /*  PREFIX
     *     Infix      ->      Prefix
     *      2+3                +23
     *      p-q                -pq
     *     a+b*c              +a*bc
     *   a*b+c*d-e          -+*ab*cde
     * */

    StringBuilder sb = new StringBuilder(reverse(exp));

    for (int i = 0; i < sb.length(); i++) {
      int charCode = sb.codePointAt(i);

      switch (charCode) {
        case 40 -> // ( -> )
            sb.setCharAt(i, (char) 41);
        case 41 -> // ) -> (
            sb.setCharAt(i, (char) 40);
        case 91 -> // [ -> ]
            sb.setCharAt(i, (char) 93);
        case 93 -> // ] -> [
            sb.setCharAt(i, (char) 91);
        case 123 -> // { -> }
            sb.setCharAt(i, (char) 125);
        case 125 -> // } -> {
            sb.setCharAt(i, (char) 123);
      }
    }

    return reverse(infixToPostfix(sb.toString(), false));
  }

  public static double evaluatePostfix(String exp) {
    if (isNullString(exp)) return INVALID_NUMBER_RESULT;

    StringBuilder sb = new StringBuilder();
    Stack<Double> stack = new Stack<>();

    for (int i = 0; i < exp.length(); i++) {
      int charCode = exp.codePointAt(i);

      if (isInvalidToken(charCode)) return INVALID_NUMBER_RESULT;

      if (Character.isDigit(charCode)) {
        sb.append(exp.charAt(i));
        continue;
      }

      if (isBasicOperator(charCode)) {
        double op2 = stack.pop();
        double op1 = stack.pop();
        stack.push(calculate(op1, op2, charCode));
        sb.setLength(0);
        sb.trimToSize();
        continue;
      }

      if (!isEmptyStringBuilder(sb)) {
        stack.push(toDouble(sb.toString()));
        sb.setLength(0);
        sb.trimToSize();
      }
    }

    return stack.pop();
  }

  public static double evaluatePrefix(String exp) {
    if (isNullString(exp)) return INVALID_NUMBER_RESULT;

    StringBuilder sb = new StringBuilder();
    Stack<Double> stack = new Stack<>();

    for (int i = exp.length() - 1; i >= 0; i--) {
      int charCode = exp.codePointAt(i);

      if (isInvalidToken(charCode)) return INVALID_NUMBER_RESULT;

      if (Character.isDigit(charCode)) {
        sb.append(exp.charAt(i));
        continue;
      }

      if (isBasicOperator(charCode)) {
        double op1 = stack.pop();
        double op2 = stack.pop();
        stack.push(calculate(op1, op2, charCode));
        sb.setLength(0);
        sb.trimToSize();
        continue;
      }

      if (!isEmptyStringBuilder(sb)) {
        stack.push(toDouble(reverse(sb.toString())));
        sb.setLength(0);
        sb.trimToSize();
      }
    }

    return stack.pop();
  }

  public static boolean areBalancedParentheses(String exp) {
    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < exp.length(); i++) {
      int charCode = exp.codePointAt(i);

      if (isOpeningParenthesis(charCode)) {
        stack.push(charCode);
        continue;
      }

      if (isClosingParenthesis(charCode) && !isCompatibleParenthesesPair(stack.pop(), charCode)) {
        return false;
      }
    }

    return true;
  }

  public static String checkValidExpAndTrimWhiteSpace(String exp) {
    if (isNullString(exp)) return "Input expression is null";
    if (!areBalancedParentheses(exp)) return "Input expression parentheses are not balanced";

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < exp.length(); i++) {
      if (isInvalidInfixToken(exp.codePointAt(i))) return "Input expression has invalid token";
      if (!Character.isWhitespace(exp.charAt(i))) {
        sb.append(exp.charAt(i));
      }
    }

    return sb.toString();
  }

  private static double calculate(double operand1, double operand2, int codePoint) {
    return switch (codePoint) {
      case 42 -> operand1 * operand2;
      case 43 -> operand1 + operand2;
      case 45 -> operand1 - operand2;
      case 47 -> operand1 / operand2;
      default -> 0;
    };
  }

  private static double toDouble(String s) {
    double num = 0;
    for (int i = 0; i < s.length(); i++) {
      num = num * 10 + (double) (s.codePointAt(i) - 48);
    }
    return num;
  }

  private static String reverse(String s) {
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < s.length(); i++) {
      stack.push(s.charAt(i));
    }
    StringBuilder res = new StringBuilder();
    while (!stack.empty()) res.append(stack.pop());
    return res.toString();
  }

  private static int getOperatorWeight(int codePoint) {
    if (codePoint == 43 || codePoint == 45) return 1;
    return 2;
  }

  private static boolean hasHigherPrecedence(int codePoint1, int codePoint2, boolean forPostfix) {
    int weightOfOp1 = getOperatorWeight(codePoint1);
    int weightOfOp2 = getOperatorWeight(codePoint2);
    return forPostfix ? weightOfOp1 >= weightOfOp2 : weightOfOp1 > weightOfOp2;
  }

  private static boolean isInvalidToken(int codePoint) {
    return !Character.isWhitespace(codePoint)
        && !Character.isDigit(codePoint)
        && !isBasicOperator(codePoint);
  }

  private static boolean isInvalidInfixToken(int codePoint) {
    return isInvalidToken(codePoint)
        && !isOpeningParenthesis(codePoint)
        && !isClosingParenthesis(codePoint);
  }

  private static boolean isEmptyStringBuilder(StringBuilder sb) {
    return sb.length() == 0;
  }

  private static boolean isNullString(String s) {
    return s == null;
  }

  private static boolean isBasicOperator(int codePoint) {
    return codePoint == 42 || codePoint == 43 || codePoint == 45 || codePoint == 47;
  }

  private static boolean isOpeningParenthesis(int codePoint) {
    return codePoint == 40 || codePoint == 91 || codePoint == 123;
  }

  private static boolean isClosingParenthesis(int codePoint) {
    return codePoint == 41 || codePoint == 93 || codePoint == 125;
  }

  private static boolean isCompatibleParenthesesPair(int codePoint1, int codePoint2) {
    return (codePoint1 == 40 && codePoint2 == 41)
        || (codePoint1 == 91 && codePoint2 == 93)
        || (codePoint1 == 123 && codePoint2 == 125);
  }
}
