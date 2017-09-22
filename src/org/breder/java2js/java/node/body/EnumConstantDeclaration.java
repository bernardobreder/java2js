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
 * Created on 05/11/2006
 */
package org.breder.java2js.java.node.body;

import java.io.IOException;
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.expr.AnnotationExpr;
import org.breder.java2js.java.node.expr.Expression;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class EnumConstantDeclaration extends BodyDeclaration {

  private final List<AnnotationExpr> annotations;

  private final String name;

  private final List<Expression> args;

  private final List<BodyDeclaration> classBody;

  public EnumConstantDeclaration(int line, int column, JavadocComment javaDoc,
    List<AnnotationExpr> annotations, String name, List<Expression> args,
    List<BodyDeclaration> classBody) {
    super(line, column, javaDoc);
    this.annotations = annotations;
    this.name = name;
    this.args = args;
    this.classBody = classBody;
  }

  public List<AnnotationExpr> getAnnotations() {
    return annotations;
  }

  public String getName() {
    return name;
  }

  public List<Expression> getArgs() {
    return args;
  }

  public List<BodyDeclaration> getClassBody() {
    return classBody;
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
