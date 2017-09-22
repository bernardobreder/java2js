package org.breder.java2js.compiler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.breder.java2js.java.node.PackageDeclaration;

/**
 * 
 * 
 * @author Tecgraf
 */
public class PrinterOutputStream extends OutputStream {

  private final OutputStream output;

  private final List<PackageDeclaration> packages =
    new ArrayList<PackageDeclaration>();

  private int tab;

  /**
   * @param output
   */
  public PrinterOutputStream(OutputStream output) {
    this.output = output;
  }

  public void pushPackage(PackageDeclaration item) {
    this.packages.add(item);
  }

  public PackageDeclaration getPackage() {
    if (this.packages.isEmpty()) {
      return null;
    }
    return this.packages.get(this.packages.size() - 1);
  }

  public void popPackage() {
    if (!this.packages.isEmpty()) {
      this.packages.remove(this.packages.size() - 1);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(int b) throws IOException {
    this.output.write(b);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    this.output.close();
  }

  /**
   * @param text
   * @throws IOException
   */
  public void write(String text) throws IOException {
    int size = text.length();
    for (int n = 0; n < size; n++) {
      char c = text.charAt(n);
      if (c <= 0x7F) {
        output.write(c);
      }
      else if (c <= 0x7FF) {
        output.write(((c >> 6) & 0x1F) + 0xC0);
        output.write((c & 0x3F) + 0x80);
      }
      else {
        output.write(((c >> 12) & 0xF) + 0xE0);
        output.write(((c >> 6) & 0x3F) + 0x80);
        output.write((c & 0x3F) + 0x80);
      }
    }

  }

  public void writeDebug(String string) throws IOException {
    this.write(string);
  }

  public void writeDebugLine(int count) throws IOException {
    for (int n = 0; n < count; n++) {
      this.writeDebug("\n");
    }
  }

  public void writeDebugTab() throws IOException {
    for (int n = 0; n < tab; n++) {
      this.writeDebug("\t");
    }
  }

  public void incDebugTab() throws IOException {
    this.tab++;
  }

  public void decDebugTab() throws IOException {
    this.tab--;
  }

}
