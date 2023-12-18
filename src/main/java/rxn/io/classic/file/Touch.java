package rxn.io.classic.file;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Touch {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Must include path in the application calling command");
      return;
    }

    ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
    new File(args[0]).setLastModified(zdt.toInstant().toEpochMilli());
  }
}
