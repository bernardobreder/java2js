/*
 * Copyright (C) 2007 J�lio Vilmar Gesser.
 * 
 * This file is part of Java 1.5 parser and Abstract Syntax Tree.
 * 
 * Java 1.5 parser and Abstract Syntax Tree is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * Java 1.5 parser and Abstract Syntax Tree is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java 1.5 parser and Abstract Syntax Tree. If not, see
 * <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 07/11/2006
 */
package org.breder.java2js.java.node.stmt;

import java.io.IOException;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.expr.Expression;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class IfStmt extends Statement {

  private final Expression condition;

  private final Statement thenStmt;

  private final Statement elseStmt;

  public IfStmt(int line, int column, Expression condition, Statement thenStmt,
    Statement elseStmt) {
    super(line, column);
    this.condition = condition;
    this.thenStmt = thenStmt;
    this.elseStmt = elseStmt;
  }

  public Expression getCondition() {
    return condition;
  }

  public Statement getThenStmt() {
    return thenStmt;
  }

  public Statement getElseStmt() {
    return elseStmt;
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
    output.write("if (");
    this.condition.writeJs(output);
    output.write(") ");
    this.thenStmt.writeJs(output);
    if (this.elseStmt != null) {
      output.write(" else ");
      this.elseStmt.writeJs(output);
    }
  }

}
