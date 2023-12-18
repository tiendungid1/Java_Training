package rxn.io.classic.stream.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Copy {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("usage: java Copy srcfile dstfile");
      return;
    }

    try (
        /*
         * FileOutputStream(String name) overwrites an existing file. To append data
         * instead of overwriting existing content, call a FileOutputStream constructor
         * that includes a boolean append parameter and pass true to this parameter.
         * */
        FileInputStream fis = new FileInputStream(args[0]);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(args[1]);
        BufferedOutputStream bos = new BufferedOutputStream(fos)
    ) {
      int b; // b is short of 'byte', but 'byte' is a reserved word so we can't use it

      while ((b = bis.read()) != -1) {
        bos.write(b);
      }
    } catch (FileNotFoundException fnfe) {
      System.err.println(args[0] + " could not be opened for input, or "
                         + args[1] + " could not be created for output");
    } catch (IOException ioe) {
      System.err.println("I/O error: " + ioe.getMessage());
    }
  }
}
