package rxn.io.classic.file;

import java.io.File;
import java.io.FilenameFilter;

public class Dir {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("usage: java Dir dirpath ext");
      return;
    }

    File file = new File(args[0]);
    FilenameFilter fnf = (dir, name) -> name.endsWith(args[1]);
    String[] names = file.list(fnf);

    for (String name : names) {
      System.out.println(name);
    }
  }
}
