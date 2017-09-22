package org.breder.java2js.java.node;

import java.io.IOException;
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.body.TypeDeclaration;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class CompilationUnit extends Node {

  private final PackageDeclaration pakage;

  private final List<ImportDeclaration> imports;

  private final List<TypeDeclaration> types;

  private final List<Comment> comments;

  public CompilationUnit(int line, int column, PackageDeclaration pakage,
    List<ImportDeclaration> imports, List<TypeDeclaration> types,
    List<Comment> comments) {
    super(line, column);
    this.pakage = pakage;
    this.imports = imports;
    this.types = types;
    this.comments = comments;
  }

  public PackageDeclaration getPakage() {
    return pakage;
  }

  public List<ImportDeclaration> getImports() {
    return imports;
  }

  public List<TypeDeclaration> getTypes() {
    return types;
  }

  public List<Comment> getComments() {
    return comments;
  }

  @Override
  public <A> void accept(VoidVisitor<A> v, A arg) {
    v.visit(this, arg);
  }

  @Override
  public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
    return v.visit(this, arg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeJs(PrinterOutputStream output) throws IOException {
    output.pushPackage(pakage);
    for (TypeDeclaration type : this.types) {
      type.writeJs(output);
    }
    output.popPackage();
  }

}
