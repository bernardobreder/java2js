package org.breder.java2js.java.node;

import java.io.IOException;
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.type.ClassOrInterfaceType;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class TypeParameter extends Node {

  private final String name;

  private final List<ClassOrInterfaceType> typeBound;

  /**
   * @param line
   * @param column
   * @param name
   * @param typeBound
   */
  public TypeParameter(int line, int column, String name,
    List<ClassOrInterfaceType> typeBound) {
    super(line, column);
    this.name = name;
    this.typeBound = typeBound;
  }

  public String getName() {
    return name;
  }

  public List<ClassOrInterfaceType> getTypeBound() {
    return typeBound;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <A> void accept(VoidVisitor<A> v, A arg) {
    v.visit(this, arg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
    return v.visit(this, arg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeJs(PrinterOutputStream output) throws IOException {
    output.write(this.name);
  }

}
