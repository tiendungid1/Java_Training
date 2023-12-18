package rxn.io.classic.randomaccessfile;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFDemo {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Must include path in file calling command");
      return;
    }

    try (RandomAccessFile raf = new RandomAccessFile(args[0], "rwd")) {
      raf.writeInt(127);
      raf.writeChars("Test");

//      Reset pointer
      raf.seek(0);

//      Reading file
      System.out.println(raf.readInt());

      for (int i = 0; i < "Test".length(); i++) {
        System.out.print(raf.readChar());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
