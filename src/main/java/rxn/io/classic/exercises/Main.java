//package rxn.io.classic.exercises;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.LineNumberReader;
//import java.nio.charset.StandardCharsets;
//import java.time.Instant;
//import java.time.ZoneOffset;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Scanner;
//
//public class Main {
//
//  public static void main(String[] args) throws IOException {
//    printTheLongestWord(new File("files\\exercisePlainText.txt"));
//  }
//
//  public static void printTheLongestWord(File file) throws IOException {
//    if (!file.isFile()) {
//      throw new IllegalArgumentException("Path must be a file");
//    }
//
//    if (!file.exists()) {
//      throw new FileNotFoundException();
//    }
//
//    try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
//      String longest = "";
//
//      while (scanner.hasNext()) {
//        String word = scanner.next();
//
//        if (longest.length() < word.length()) {
//          longest = word;
//        }
//      }
//
//      System.out.println("Longest word: " + longest);
//    }
//  }
//
//  public static void readTheFirstThreeLines(File file) throws IOException {
//    if (!file.isFile()) {
//      throw new IllegalArgumentException("Path must be a file");
//    }
//
//    if (!file.exists()) {
//      throw new FileNotFoundException();
//    }
//
//    try (
//        FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
//        LineNumberReader lnr = new LineNumberReader(fr)
//    ) {
//      String line;
//
//      while ((line = lnr.readLine()) != null && lnr.getLineNumber() <= 3) {
//        System.out.println(line);
//      }
//    }
//  }
//
//  public static void writeToExistingFile(File file) throws IOException {
//    String[] msgs = {
//        "hello",
//        "world",
//        "my friend"
//    };
//
//    if (!file.isFile()) {
//      throw new IllegalArgumentException("Path must be a file");
//    }
//
//    if (!file.exists()) {
//      throw new FileNotFoundException();
//    }
//
//    try (
//        FileWriter fw = new FileWriter(file, true);
//        BufferedWriter bw = new BufferedWriter(fw)
//    ) {
//      for (String msg : msgs) {
//        bw.write(msg);
//        bw.newLine();
//      }
//    }
//  }
//
//  public static void writeAndReadPlainTextFile() throws IOException {
//    File inputFile = new File("files\\bwbrdemo");
//
//    if (!inputFile.isFile()) {
//      throw new IllegalArgumentException("Path must be a file");
//    }
//
//    if (!inputFile.exists()) {
//      throw new FileNotFoundException();
//    }
//
//    try (
//        FileWriter fw = new FileWriter("files\\exercisePlainText.txt", StandardCharsets.UTF_8);
//        BufferedWriter bw = new BufferedWriter(fw);
//        FileReader fr = new FileReader(inputFile);
//        BufferedReader br = new BufferedReader(fr)
//    ) {
//      String out;
//      while ((out = br.readLine()) != null) {
//        bw.write(out);
//        bw.newLine();
//      }
//    }
//
//    try (
//        FileReader fr = new FileReader("files\\exercisePlainText.txt");
//        BufferedReader br = new BufferedReader(fr)
//    ) {
//      String line;
//
//      while ((line = br.readLine()) != null) {
//        System.out.println(line);
//      }
//    }
//  }
//
//  public static void readFileLineByLineAndSaveData() throws IOException {
//    try (
//        FileReader fr = new FileReader("files\\bwbrdemo");
//        BufferedReader br = new BufferedReader(fr)
//    ) {
//      StringBuilder data = new StringBuilder();
//      String line;
//
//      while ((line = br.readLine()) != null) {
//        data.append(line);
//        data.append(System.lineSeparator());
//      }
//
//      System.out.println(data);
//    }
//  }
//
//  public static void readFileLineByLine() throws IOException {
//    try (
//        FileReader fr = new FileReader("files\\bwbrdemo");
//        BufferedReader br = new BufferedReader(fr)
//    ) {
//      String line;
//      int count = 1;
//
//      while ((line = br.readLine()) != null) {
//        System.out.println("Line " + count++ + ": " + line);
//      }
//    }
//  }
//
//  public static void readFileToByteArray() throws IOException {
//    File file = new File("files\\bwbrdemo");
//    byte[] fileContent = new byte[(int) file.length()];
//
//    try (
//        FileInputStream fis = new FileInputStream(file);
//        BufferedInputStream bis = new BufferedInputStream(fis)
//    ) {
//      bis.read(fileContent);
//      System.out.println(new String(fileContent, StandardCharsets.UTF_8));
//    }
//  }
//
//  public static void printFileSize() throws IOException {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      System.out.println("Enter path of file");
//      File file = new File(br.readLine());
//      long sizeInByte = file.length();
//
//      if (sizeInByte == 0) {
//        System.out.println("File or Dir not exist");
//      } else {
//        System.out.println("Size in byte = " + sizeInByte);
//        System.out.println("Size in kb = " + (double) (sizeInByte / 1024));
//        System.out.println("Size in mb = " + (double) (sizeInByte / 1024 / 1024));
//      }
//    }
//  }
//
//  public static void printLastModifiedTime() throws IOException {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      System.out.println("Enter path of file");
//      long millisecondsSinceEpoch = new File(br.readLine()).lastModified();
//      Instant instant = Instant.ofEpochMilli(millisecondsSinceEpoch);
//      ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
//      DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
//      System.out.println(formatter.format(zdt));
//    }
//  }
//
//  public static void compareTwoFilePaths() throws IOException {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      System.out.println("Enter path of file 1");
//      File file1 = new File(br.readLine());
//      System.out.println("Enter path of file 2");
//      File file2 = new File(br.readLine());
//
//      if (file1.getCanonicalPath().equals(file2.getCanonicalPath())) {
//        System.out.println("Files are equal");
//      } else {
//        System.out.println("Files are not equal");
//      }
//    }
//  }
//
//  public static void checkPathnameEitherFileOrDirectory() throws IOException {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      System.out.println("Enter path");
//      File file = new File(br.readLine());
//
//      if (file.isFile()) {
//        System.out.println("Path is file");
//      } else {
//        System.out.println("Path is directory");
//      }
//    }
//  }
//
//  public static void checkIfFileOrDirHasReadAndWritePermission() throws IOException {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      System.out.println("Enter path");
//      File file = new File(br.readLine());
//      file.setReadOnly();
//
//      if (file.canRead() && file.canWrite()) {
//        System.out.println("File or Directory has read and write permission");
//      } else if (file.canRead()) {
//        System.out.println("File or Directory has read but not write permission");
//      } else if (file.canWrite()) {
//        System.out.println("File or Directory has write but not read permission");
//      } else {
//        System.out.println("File or Directory does not have read and write permission");
//      }
//    }
//  }
//
//  public static void checkIfFileOrDirectoryExists() throws IOException {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      System.out.println("Enter path");
//      File file = new File(br.readLine());
//
//      if (file.exists()) {
//        System.out.println("File or Directory exists");
//      } else {
//        System.out.println("File or Directory not exist");
//      }
//    }
//  }
//
//  public static void printFilesAndDirectoriesName() throws IOException {
//    String[] names = new File("files").list();
//
//    if (names == null) {
//      System.out.println("No files or directories in given path");
//      return;
//    }
//
//    for (String name : names) {
//      System.out.println(name);
//    }
//  }
//
//  public static void printFilesByExtension() throws IOException {
//    try (
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr)
//    ) {
//      System.out.println("Enter relative path of directory");
//      File file = new File(br.readLine());
//
//      System.out.println("Enter extension to filter");
//      String ext = br.readLine();
//
//      String[] fileNames = file.list((dir, name) -> name.toLowerCase().endsWith(ext));
//
//      System.out.println("Print files satisfy filter");
//
//      if (fileNames == null) {
//        System.out.println("No files");
//        return;
//      }
//
//      for (String name : fileNames) {
//        System.out.println(name);
//      }
//    }
//  }
//}
