package rxn.io.classic.stream.buffered;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BufferedInDemo {
  public static void main(String[] args) {
    try (
        FileInputStream fis = new FileInputStream("files\\data.dat");
        BufferedInputStream bis = new BufferedInputStream(fis)
    ) {
      int data = bis.read();
      System.out.println(data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
