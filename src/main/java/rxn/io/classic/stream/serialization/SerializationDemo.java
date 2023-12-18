package rxn.io.classic.stream.serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializationDemo {
  final static String FILENAME = "files\\employee.dat";

  public static void main(String[] args) {
    ObjectOutputStream oos = null;

    try {
      FileOutputStream fos = new FileOutputStream(FILENAME);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(new Employee("John Doe", 36));
      oos.close();
    } catch (IOException cnfe) {
      System.err.println(cnfe.getMessage());
    } finally {
      if (oos != null) {
        try {
          oos.close();
        } catch (IOException ioe) {
          assert false; // shouldn't happen in this context
        }
      }
    }
  }
}
