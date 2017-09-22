package org.breder.java2js.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.breder.java2js.java.JavaParser;
import org.breder.java2js.java.node.CompilationUnit;

public class Main {

  private int a;

  public Main(int b, int c) {
    this.a = 1;
  }

  public static void main(String[] args) throws Exception {
    //    List<CompilationUnit> list = new ArrayList<CompilationUnit>();
    //    List<File> files = searchFiles(new File("src"));
    //    Collections.sort(files);
    //    for (File file : files) {
    //      System.out.println(file.toString() + "...");
    //      execute(file);
    //    }
    execute(new File("src/org/breder/java2js/compiler/Main.java"));
  }

  private static void execute(File file) throws Exception {
    CompilationUnit unit = JavaParser.parse(file);
    //    String pathname = "";
    //    if (unit.getPakage() != null) {
    //      pathname =
    //        unit.getPakage().getName().toString().replace('.', File.separatorChar);
    //    }
    //    String classname =
    //      file.getName().substring(0, file.getName().length() - ".java".length());
    //    classname += ".js";
    //    File jsoFile = new File(new File("jso", pathname), classname);
    //    FileOutputStream output = new FileOutputStream(jsoFile);
    //    try {
    unit.writeJs(new PrinterOutputStream(System.out));
    //    }
    //    finally {
    //      output.close();
    //    }
  }

  private static List<File> searchFiles(File dir) {
    List<File> queue = new ArrayList<File>(64);
    queue.addAll(Arrays.asList(dir.listFiles()));
    List<File> list = new ArrayList<File>();
    for (int n = 0; n < queue.size(); n++) {
      File file = queue.get(n);
      if (!file.isHidden()) {
        if (file.isFile()) {
          if (file.getName().endsWith(".java")) {
            list.add(file);
          }
        }
        else {
          File[] files = file.listFiles();
          if (files != null) {
            queue.addAll(Arrays.asList(files));
          }
        }
      }
    }
    return list;
  }

}
