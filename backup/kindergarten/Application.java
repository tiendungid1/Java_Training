package rxn.jdbc.core.kindergarten;

import java.util.Scanner;
import rxn.jdbc.core.kindergarten.config.AppBundle;

public class Application {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      new AppBundle(scanner).bootUp();
    }
  }
}
