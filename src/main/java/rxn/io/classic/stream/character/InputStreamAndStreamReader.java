package rxn.io.classic.stream.character;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputStreamAndStreamReader {
  public static void main(String[] args) throws IOException {
    FileInputStream fis = new FileInputStream("files\\polish.txt");
    InputStreamReader isr = new InputStreamReader(fis, "8859_2");
    char c = (char) isr.read();
    System.out.println(c);
  }
}
