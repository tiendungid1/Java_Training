package rxn.io.classic.stream.serialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializationDemo {
  final static String FILENAME = "files\\employee.dat";

  public static void main(String[] args) {
    ObjectInputStream ois = null;

    try {
      FileInputStream fis = new FileInputStream(FILENAME);
      ois = new ObjectInputStream(fis);
      Employee emp = (Employee) ois.readObject(); // (Employee) cast is necessary.
      ois.close();

      System.out.println(emp.getName());
      System.out.println(emp.getAge());
    } catch (ClassNotFoundException | IOException cnfe) {
      System.err.println(cnfe.getMessage());
    } finally {
      if (ois != null) {
        try {
          ois.close();
        } catch (IOException ioe) {
          assert false; // shouldn't happen in this context
        }
      }
    }
  }
}
