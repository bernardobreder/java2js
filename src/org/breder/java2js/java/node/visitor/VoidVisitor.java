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
package org.breder.java2js.java.node.visitor;

import org.breder.java2js.java.node.BlockComment;
import org.breder.java2js.java.node.CompilationUnit;
import org.breder.java2js.java.node.ImportDeclaration;
import org.breder.java2js.java.node.LineComment;
import org.breder.java2js.java.node.Node;
import org.breder.java2js.java.node.PackageDeclaration;
import org.breder.java2js.java.node.TypeParameter;
import org.breder.java2js.java.node.body.AnnotationDeclaration;
import org.breder.java2js.java.node.body.AnnotationMemberDeclaration;
import org.breder.java2js.java.node.body.ClassOrInterfaceDeclaration;
import org.breder.java2js.java.node.body.ConstructorDeclaration;
import org.breder.java2js.java.node.body.EmptyMemberDeclaration;
import org.breder.java2js.java.node.body.EmptyTypeDeclaration;
import org.breder.java2js.java.node.body.EnumConstantDeclaration;
import org.breder.java2js.java.node.body.EnumDeclaration;
import org.breder.java2js.java.node.body.FieldDeclaration;
import org.breder.java2js.java.node.body.InitializerDeclaration;
import org.breder.java2js.java.node.body.JavadocComment;
import org.breder.java2js.java.node.body.MethodDeclaration;
import org.breder.java2js.java.node.body.Parameter;
import org.breder.java2js.java.node.body.VariableDeclarator;
import org.breder.java2js.java.node.body.VariableDeclaratorId;
import org.breder.java2js.java.node.expr.ArrayAccessExpr;
import org.breder.java2js.java.node.expr.ArrayCreationExpr;
import org.breder.java2js.java.node.expr.ArrayInitializerExpr;
import org.breder.java2js.java.node.expr.AssignExpr;
import org.breder.java2js.java.node.expr.BinaryExpr;
import org.breder.java2js.java.node.expr.BooleanLiteralExpr;
import org.breder.java2js.java.node.expr.CastExpr;
import org.breder.java2js.java.node.expr.CharLiteralExpr;
import org.breder.java2js.java.node.expr.ClassExpr;
import org.breder.java2js.java.node.expr.ConditionalExpr;
import org.breder.java2js.java.node.expr.DoubleLiteralExpr;
import org.breder.java2js.java.node.expr.EnclosedExpr;
import org.breder.java2js.java.node.expr.FieldAccessExpr;
import org.breder.java2js.java.node.expr.InstanceOfExpr;
import org.breder.java2js.java.node.expr.IntegerLiteralExpr;
import org.breder.java2js.java.node.expr.IntegerLiteralMinValueExpr;
import org.breder.java2js.java.node.expr.LongLiteralExpr;
import org.breder.java2js.java.node.expr.LongLiteralMinValueExpr;
import org.breder.java2js.java.node.expr.MarkerAnnotationExpr;
import org.breder.java2js.java.node.expr.MemberValuePair;
import org.breder.java2js.java.node.expr.MethodCallExpr;
import org.breder.java2js.java.node.expr.NameExpr;
import org.breder.java2js.java.node.expr.NormalAnnotationExpr;
import org.breder.java2js.java.node.expr.NullLiteralExpr;
import org.breder.java2js.java.node.expr.ObjectCreationExpr;
import org.breder.java2js.java.node.expr.QualifiedNameExpr;
import org.breder.java2js.java.node.expr.SingleMemberAnnotationExpr;
import org.breder.java2js.java.node.expr.StringLiteralExpr;
import org.breder.java2js.java.node.expr.SuperExpr;
import org.breder.java2js.java.node.expr.SuperMemberAccessExpr;
import org.breder.java2js.java.node.expr.ThisExpr;
import org.breder.java2js.java.node.expr.UnaryExpr;
import org.breder.java2js.java.node.expr.VariableDeclarationExpr;
import org.breder.java2js.java.node.stmt.AssertStmt;
import org.breder.java2js.java.node.stmt.BlockStmt;
import org.breder.java2js.java.node.stmt.BreakStmt;
import org.breder.java2js.java.node.stmt.CatchClause;
import org.breder.java2js.java.node.stmt.ContinueStmt;
import org.breder.java2js.java.node.stmt.DoStmt;
import org.breder.java2js.java.node.stmt.EmptyStmt;
import org.breder.java2js.java.node.stmt.ExplicitConstructorInvocationStmt;
import org.breder.java2js.java.node.stmt.ExpressionStmt;
import org.breder.java2js.java.node.stmt.ForStmt;
import org.breder.java2js.java.node.stmt.ForeachStmt;
import org.breder.java2js.java.node.stmt.IfStmt;
import org.breder.java2js.java.node.stmt.LabeledStmt;
import org.breder.java2js.java.node.stmt.ReturnStmt;
import org.breder.java2js.java.node.stmt.SwitchEntryStmt;
import org.breder.java2js.java.node.stmt.SwitchStmt;
import org.breder.java2js.java.node.stmt.SynchronizedStmt;
import org.breder.java2js.java.node.stmt.ThrowStmt;
import org.breder.java2js.java.node.stmt.TryStmt;
import org.breder.java2js.java.node.stmt.TypeDeclarationStmt;
import org.breder.java2js.java.node.stmt.WhileStmt;
import org.breder.java2js.java.node.type.ClassOrInterfaceType;
import org.breder.java2js.java.node.type.PrimitiveType;
import org.breder.java2js.java.node.type.ReferenceType;
import org.breder.java2js.java.node.type.VoidType;
import org.breder.java2js.java.node.type.WildcardType;

/**
 * @author Julio Vilmar Gesser
 */
public interface VoidVisitor<A> {

  public void visit(Node n, A arg);

  //- Compilation Unit ----------------------------------

  public void visit(CompilationUnit n, A arg);

  public void visit(PackageDeclaration n, A arg);

  public void visit(ImportDeclaration n, A arg);

  public void visit(TypeParameter n, A arg);

  public void visit(LineComment n, A arg);

  public void visit(BlockComment n, A arg);

  //- Body ----------------------------------------------

  public void visit(ClassOrInterfaceDeclaration n, A arg);

  public void visit(EnumDeclaration n, A arg);

  public void visit(EmptyTypeDeclaration n, A arg);

  public void visit(EnumConstantDeclaration n, A arg);

  public void visit(AnnotationDeclaration n, A arg);

  public void visit(AnnotationMemberDeclaration n, A arg);

  public void visit(FieldDeclaration n, A arg);

  public void visit(VariableDeclarator n, A arg);

  public void visit(VariableDeclaratorId n, A arg);

  public void visit(ConstructorDeclaration n, A arg);

  public void visit(MethodDeclaration n, A arg);

  public void visit(Parameter n, A arg);

  public void visit(EmptyMemberDeclaration n, A arg);

  public void visit(InitializerDeclaration n, A arg);

  public void visit(JavadocComment n, A arg);

  //- Type ----------------------------------------------

  public void visit(ClassOrInterfaceType n, A arg);

  public void visit(PrimitiveType n, A arg);

  public void visit(ReferenceType n, A arg);

  public void visit(VoidType n, A arg);

  public void visit(WildcardType n, A arg);

  //- Expression ----------------------------------------

  public void visit(ArrayAccessExpr n, A arg);

  public void visit(ArrayCreationExpr n, A arg);

  public void visit(ArrayInitializerExpr n, A arg);

  public void visit(AssignExpr n, A arg);

  public void visit(BinaryExpr n, A arg);

  public void visit(CastExpr n, A arg);

  public void visit(ClassExpr n, A arg);

  public void visit(ConditionalExpr n, A arg);

  public void visit(EnclosedExpr n, A arg);

  public void visit(FieldAccessExpr n, A arg);

  public void visit(InstanceOfExpr n, A arg);

  public void visit(StringLiteralExpr n, A arg);

  public void visit(IntegerLiteralExpr n, A arg);

  public void visit(LongLiteralExpr n, A arg);

  public void visit(IntegerLiteralMinValueExpr n, A arg);

  public void visit(LongLiteralMinValueExpr n, A arg);

  public void visit(CharLiteralExpr n, A arg);

  public void visit(DoubleLiteralExpr n, A arg);

  public void visit(BooleanLiteralExpr n, A arg);

  public void visit(NullLiteralExpr n, A arg);

  public void visit(MethodCallExpr n, A arg);

  public void visit(NameExpr n, A arg);

  public void visit(ObjectCreationExpr n, A arg);

  public void visit(QualifiedNameExpr n, A arg);

  public void visit(SuperMemberAccessExpr n, A arg);

  public void visit(ThisExpr n, A arg);

  public void visit(SuperExpr n, A arg);

  public void visit(UnaryExpr n, A arg);

  public void visit(VariableDeclarationExpr n, A arg);

  public void visit(MarkerAnnotationExpr n, A arg);

  public void visit(SingleMemberAnnotationExpr n, A arg);

  public void visit(NormalAnnotationExpr n, A arg);

  public void visit(MemberValuePair n, A arg);

  //- Statements ----------------------------------------

  public void visit(ExplicitConstructorInvocationStmt n, A arg);

  public void visit(TypeDeclarationStmt n, A arg);

  public void visit(AssertStmt n, A arg);

  public void visit(BlockStmt n, A arg);

  public void visit(LabeledStmt n, A arg);

  public void visit(EmptyStmt n, A arg);

  public void visit(ExpressionStmt n, A arg);

  public void visit(SwitchStmt n, A arg);

  public void visit(SwitchEntryStmt n, A arg);

  public void visit(BreakStmt n, A arg);

  public void visit(ReturnStmt n, A arg);

  public void visit(IfStmt n, A arg);

  public void visit(WhileStmt n, A arg);

  public void visit(ContinueStmt n, A arg);

  public void visit(DoStmt n, A arg);

  public void visit(ForeachStmt n, A arg);

  public void visit(ForStmt n, A arg);

  public void visit(ThrowStmt n, A arg);

  public void visit(SynchronizedStmt n, A arg);

  public void visit(TryStmt n, A arg);

  public void visit(CatchClause n, A arg);

}
