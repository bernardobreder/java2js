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
public final class AssignExpr extends Expression {

  public static enum Operator {
    assign, // =
    plus, // +=
    minus, // -=
    star, // *=
    slash, // /=
    and, // &=
    or, // |=
    xor, // ^=
    rem, // %=
    lShift, // <<=
    rSignedShift, // >>=
    rUnsignedShift, // >>>=
  }

  private final Expression target;

  private final Expression value;

  private final Operator op;

  public AssignExpr(int line, int column, Expression target, Expression value,
    Operator op) {
    super(line, column);
    this.target = target;
    this.value = value;
    this.op = op;
  }

  public Expression getTarget() {
    return target;
  }

  public Expression getValue() {
    return value;
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
    this.target.writeJs(output);
    output.writeDebug(" ");
    switch (this.op) {
      case assign:
        output.write("=");
        break; // =
      case plus:
        output.write("+=");
        break; // +=
      case minus:
        output.write("-=");
        break; // -=
      case star:
        output.write("*=");
        break; // *=
      case slash:
        output.write("/=");
        break; // /=
      case and:
        output.write("&&");
        break; // &=
      case or:
        output.write("||");
        break; // |=
      case xor:
        output.write("^=");
        break; // ^=
      case rem:
        output.write("%=");
        break; // %=
      case lShift:
        output.write("<<=");
        break; // <<=
      case rSignedShift:
        output.write(">>=");
        break; // >>=
      case rUnsignedShift:
        output.write(">>>=");
        break; // >>>=
    }
    output.writeDebug(" ");
    this.value.writeJs(output);
  }

}
