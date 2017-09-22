package org.breder.java2js.java.node.stmt;

import java.io.IOException;
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class BlockStmt extends Statement {

  private final List<Statement> stmts;

  public BlockStmt(int beginLine, int beginColumn, int endLine, int endColumn,
    List<Statement> stmts) {
    super(beginLine, beginColumn, endLine, endColumn);
    this.stmts = stmts;
  }

  public List<Statement> getStmts() {
    return stmts;
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
    if (stmts == null) {
      output.write(';');
    }
    else {
      output.write("{");
      output.writeDebugLine(1);
      output.incDebugTab();
      for (Statement stmt : stmts) {
        output.writeDebugTab();
        stmt.writeJs(output);
        output.writeDebugLine(1);
      }
      output.decDebugTab();
      output.writeDebugTab();
      output.write("}");
    }
  }

}
