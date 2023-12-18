package rxn.io.classic.stream.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Split {
  private static final int FILE_SIZE = 1_400_000;
  private static final byte[] buffer = new byte[FILE_SIZE];

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Running file command 'java Split' must contain the path argument");
      return;
    }

    File file = new File(args[0]);
    long length = file.length();
    int partCount = (int) (length / FILE_SIZE);
    int remainder = (int) (length % FILE_SIZE);

    System.out.printf("Splitting %s into %d parts%n", file,
        remainder == 0 ? partCount : partCount + 1);

    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;

    try {
      FileInputStream fis = new FileInputStream(file);
      bis = new BufferedInputStream(fis);

      for (int i = 1; i <= partCount; i++) {
        bis.read(buffer);
        System.out.println("Writing part " + i);

        FileOutputStream fos = new FileOutputStream("files\\part" + i);
        bos = new BufferedOutputStream(fos);
        bos.write(buffer);
        bos.close();
      }

      if (remainder != 0) {
        int b = bis.read(buffer);

        if (b != remainder) {
          System.err.println("Last part mismatch: expected " + remainder + " bytes");
          System.exit(0);
        }

        System.out.println("Writing part " + (partCount + 1));
        FileOutputStream fos = new FileOutputStream("files\\part" + (partCount + 1));
        bos = new BufferedOutputStream(fos);
        bos.write(buffer, 0, remainder);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bis != null) {
        try {
          bis.close();
        } catch (IOException e) {
          assert false;
        }
      }

      if (bos != null) {
        try {
          bos.close();
        } catch (IOException e) {
          assert false;
        }
      }
    }
  }
}
