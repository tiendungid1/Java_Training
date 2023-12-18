package rxn.io.classic.stream.buffered;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedOutDemo {
  public static void main(String[] args) {
    try (
        FileOutputStream fos = new FileOutputStream("files\\data.dat");
        BufferedOutputStream bos = new BufferedOutputStream(fos)
    ) {
      bos.write(120);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
