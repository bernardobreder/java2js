package org.breder.java2js.java;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.breder.java2js.java.node.CompilationUnit;
import org.junit.Test;

public class JavaParserTest {

  @Test
  public void test() throws Exception {
    List<CompilationUnit> list = new ArrayList<CompilationUnit>();
    List<File> files = searchFiles(new File(".."));
    Collections.sort(files);
    for (File file : files) {
      System.out.println(file.toString() + "...");
      FileInputStream input = new FileInputStream(file);
      try {
        JavaParser.parse(file);
      }
      finally {
        input.close();
      }
    }
  }

  private List<File> searchFiles(File dir) {
    List<File> queue = new ArrayList<File>();
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
