package rxn.io.classic.stream.character;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//  Create a Java application named CircleInfo that, after
//  obtaining a BufferedReader instance that is chained to standard
//  input, presents a loop that prompts the user to enter a radius, parses
//  the entered radius into a double value, and outputs a pair of messages
//  that report the circle’s circumference and area based on this radius.
public class CircleInfo {
  public static void main(String[] args) throws IOException {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);

    while (true) {
      System.out.print("Enter circle’s radius: ");
      String str = br.readLine();
      double radius;

      try {
        radius = Double.parseDouble(str);

        if (radius <= 0) {
          System.err.println("radius must not be 0 or negative");
        } else {
          System.out.println("Circumference: " + Math.PI * 2.0 * radius);
          System.out.println("Area: " + Math.PI * radius * radius);
          System.out.println();
        }
      } catch (NumberFormatException nfe) {
        System.err.println("not a number: " + nfe.getMessage());
      }
    }
  }
}
