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
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.type.Type;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class MethodCallExpr extends Expression {

  private final Expression scope;

  private final List<Type> typeArgs;

  private final String name;

  private final List<Expression> args;

  public MethodCallExpr(int line, int column, Expression scope,
    List<Type> typeArgs, String name, List<Expression> args) {
    super(line, column);
    this.scope = scope;
    this.typeArgs = typeArgs;
    this.name = name;
    this.args = args;
  }

  public Expression getScope() {
    return scope;
  }

  public List<Type> getTypeArgs() {
    return typeArgs;
  }

  public String getName() {
    return name;
  }

  public List<Expression> getArgs() {
    return args;
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
    if (this.scope != null) {
      this.scope.writeJs(output);
      output.write('.');
    }
    output.write(this.name);
    output.write('(');
    if (this.args != null) {
      for (int n = 0; n < this.args.size(); n++) {
        this.args.get(n).writeJs(output);
        if (n != this.args.size() - 1) {
          output.write(',');
          output.write(' ');
        }
      }
    }
    output.write(')');
  }

}