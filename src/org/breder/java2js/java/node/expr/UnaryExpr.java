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
 * Created on 05/10/2006
 */
package org.breder.java2js.java.node.expr;

import java.io.IOException;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class UnaryExpr extends Expression {

  public static enum Operator {
    positive, // +
    negative, // -
    preIncrement, // ++
    preDecrement, // --
    not, // !
    inverse, // ~
    posIncrement, // ++
    posDecrement, // --
  }

  private final Expression expr;

  private final Operator op;

  public UnaryExpr(int line, int column, Expression expr, Operator op) {
    super(line, column);
    this.expr = expr;
    this.op = op;
  }

  public Expression getExpr() {
    return expr;
  }

  public Operator getOperator() {
    return op;
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
    switch (this.op) {
      case positive:
        this.expr.writeJs(output);
        break; // +
      case negative:
        output.write('-');
        this.expr.writeJs(output);
        break; // -
      case preIncrement:
        output.write('+');
        output.write('+');
        this.expr.writeJs(output);
        break; // ++
      case preDecrement:
        output.write('-');
        output.write('-');
        this.expr.writeJs(output);
        break; // --
      case not:
        output.write('!');
        this.expr.writeJs(output);
        break; // !
      case inverse:
        output.write('~');
        this.expr.writeJs(output);
        break; // ~
      case posIncrement:
        this.expr.writeJs(output);
        output.write('+');
        output.write('+');
        break; // ++
      case posDecrement:
        this.expr.writeJs(output);
        output.write('-');
        output.write('-');
        break; // --
    }
  }

}
