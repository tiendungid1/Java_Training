package rxn.io.classic.file;

import java.io.File;
import java.util.Date;

public class FileDirectoryInfo {
  public static void main(String[] args) {
    File file = new File(args[0]);
    System.out.println("About " + file + ":");
    System.out.println("Exists = " + file.exists());
    System.out.println("Is directory = " + file.isDirectory());
    System.out.println("Is file = " + file.isFile());
    System.out.println("Is hidden = " + file.isHidden());
    System.out.println("Last modified = " +
                       new Date(file.lastModified()));
    System.out.println("Length = " + file.length());
  }
}
