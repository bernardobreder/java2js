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
import org.breder.java2js.java.node.expr.VariableDeclarationExpr;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class ForeachStmt extends Statement {

  private final VariableDeclarationExpr var;

  private final Expression iterable;

  private final Statement body;

  public ForeachStmt(int line, int column, VariableDeclarationExpr var,
    Expression iterable, Statement body) {
    super(line, column);
    this.var = var;
    this.iterable = iterable;
    this.body = body;
  }

  public VariableDeclarationExpr getVariable() {
    return var;
  }

  public Expression getIterable() {
    return iterable;
  }

  public Statement getBody() {
    return body;
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
  }

}