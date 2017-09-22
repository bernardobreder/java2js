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
public final class BinaryExpr extends Expression {

  public static enum Operator {
    or, // ||
    and, // &&
    binOr, // |
    binAnd, // &
    xor, // ^
    equals, // ==
    notEquals, // !=
    less, // <
    greater, // >
    lessEquals, // <=
    greaterEquals, // >=
    lShift, // <<
    rSignedShift, // >>
    rUnsignedShift, // >>>
    plus, // +
    minus, // -
    times, // *
    divide, // /
    remainder, // %
  }

  private final Expression left;

  private final Expression right;

  private final Operator op;

  public BinaryExpr(int line, int column, Expression left, Expression right,
    Operator op) {
    super(line, column);
    this.left = left;
    this.right = right;
    this.op = op;
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
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
    this.left.writeJs(output);
    output.write(" ");
    switch (this.op) {
      case or:
        output.write("||");
        break; // ||
      case and:
        output.write("&&");
        break; // &&
      case binOr:
        output.write("|");
        break; // |
      case binAnd:
        output.write("&");
        break; // &
      case xor:
        output.write("^");
        break; // ^
      case equals:
        output.write("==");
        break; // ==
      case notEquals:
        output.write("!=");
        break; // !=
      case less:
        output.write("<");
        break; // <
      case greater:
        output.write(">");
        break; // >
      case lessEquals:
        output.write("<=");
        break; // <=
      case greaterEquals:
        output.write(">=");
        break; // >=
      case lShift:
        output.write("<<");
        break; // <<
      case rSignedShift:
        output.write(">>");
        break; // >>
      case rUnsignedShift:
        output.write(">>>");
        break; // >>>
      case plus:
        output.write("+");
        break; // +
      case minus:
        output.write("-");
        break; // -
      case times:
        output.write("*");
        break; // *
      case divide:
        output.write("/");
        break; // /
      case remainder:
        output.write("%");
        break; // %      
    }
    output.write(" ");
    this.right.writeJs(output);
  }

}
