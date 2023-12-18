package rxn.io.classic.stream.character;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class OutputStreamAndStreamWriter {
  public static void main(String[] args) throws IOException {
    FileOutputStream fos = new FileOutputStream("files\\polish.txt");
    OutputStreamWriter osw = new OutputStreamWriter(fos, "8859_2");
    char c = '\u0323';
    osw.write(c);
  }
}
