//package rxn.io.classic.exercises;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FileLinesCounter {
//  public static void main(String[] args) {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      List<File> files = new ArrayList<>();
//
//      do {
//        System.out.println("Enter file path");
//        files.add(new File(br.readLine()));
//      } while (getContinueCommand(br).equals("y"));
//
//      countLines(files);
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//  public static void countLines(List<File> files) throws IOException {
//    for (File file : files) {
//      if (!file.exists()) {
//        System.out.println(file.getCanonicalPath() + " not exist");
//        continue;
//      }
//
//      if (!file.isFile()) {
//        System.out.println(file.getCanonicalPath() + " is not a file");
//        continue;
//      }
//
//      showFileNameAndLineNumbers(file);
//    }
//  }
//
//  public static void showFileNameAndLineNumbers(File file) {
//    try (
//        FileReader fileReader = new FileReader(file);
//        BufferedReader bufferedReader = new BufferedReader(fileReader)
//    ) {
//      int count = 0;
//      while (bufferedReader.readLine() != null) {
//        count++;
//      }
//      System.out.println(file.getCanonicalPath() + " has " + count + " lines");
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//  public static String getContinueCommand(BufferedReader bufferedReader) throws IOException {
//    while (true) {
//      System.out.println("Press 'y' to continue input, or 'q' to quit");
//      String command = bufferedReader.readLine();
//
//      if (!command.equals("y") && !command.equals("q")) {
//        Console.emphasize("Input '" + command + "' is invalid");
//        Console.emphasize("Please enter 'y' or 'q' only");
//        continue;
//      }
//
//      return command;
//    }
//  }
//}
