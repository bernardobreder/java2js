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
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.expr.Expression;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class ForStmt extends Statement {

  private final List<Expression> init;

  private final Expression compare;

  private final List<Expression> update;

  private final Statement body;

  public ForStmt(int line, int column, List<Expression> init,
    Expression compare, List<Expression> update, Statement body) {
    super(line, column);
    this.compare = compare;
    this.init = init;
    this.update = update;
    this.body = body;
  }

  public List<Expression> getInit() {
    return init;
  }

  public Expression getCompare() {
    return compare;
  }

  public List<Expression> getUpdate() {
    return update;
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
    output.write("for ( ");
    if (this.init != null) {
      for (int n = 0; n < this.init.size(); n++) {
        Expression init = this.init.get(n);
        init.writeJs(output);
      }
    }
    output.write(' ');
    output.write(';');
    output.write(' ');
    if (this.compare != null) {
      this.compare.writeJs(output);
      output.write(' ');
    }
    output.write(';');
    output.write(' ');
    if (this.update != null) {
      for (int n = 0; n < this.update.size(); n++) {
        Expression update = this.update.get(n);
        update.writeJs(output);
      }
    }
    output.write(' ');
    output.write(')');
    this.body.writeJs(output);
  }

}
