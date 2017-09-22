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
public interface GenericVisitor<R, A> {

  public R visit(Node n, A arg);

  //- Compilation Unit ----------------------------------

  public R visit(CompilationUnit n, A arg);

  public R visit(PackageDeclaration n, A arg);

  public R visit(ImportDeclaration n, A arg);

  public R visit(TypeParameter n, A arg);

  public R visit(LineComment n, A arg);

  public R visit(BlockComment n, A arg);

  //- Body ----------------------------------------------

  public R visit(ClassOrInterfaceDeclaration n, A arg);

  public R visit(EnumDeclaration n, A arg);

  public R visit(EmptyTypeDeclaration n, A arg);

  public R visit(EnumConstantDeclaration n, A arg);

  public R visit(AnnotationDeclaration n, A arg);

  public R visit(AnnotationMemberDeclaration n, A arg);

  public R visit(FieldDeclaration n, A arg);

  public R visit(VariableDeclarator n, A arg);

  public R visit(VariableDeclaratorId n, A arg);

  public R visit(ConstructorDeclaration n, A arg);

  public R visit(MethodDeclaration n, A arg);

  public R visit(Parameter n, A arg);

  public R visit(EmptyMemberDeclaration n, A arg);

  public R visit(InitializerDeclaration n, A arg);

  public R visit(JavadocComment n, A arg);

  //- Type ----------------------------------------------

  public R visit(ClassOrInterfaceType n, A arg);

  public R visit(PrimitiveType n, A arg);

  public R visit(ReferenceType n, A arg);

  public R visit(VoidType n, A arg);

  public R visit(WildcardType n, A arg);

  //- Expression ----------------------------------------

  public R visit(ArrayAccessExpr n, A arg);

  public R visit(ArrayCreationExpr n, A arg);

  public R visit(ArrayInitializerExpr n, A arg);

  public R visit(AssignExpr n, A arg);

  public R visit(BinaryExpr n, A arg);

  public R visit(CastExpr n, A arg);

  public R visit(ClassExpr n, A arg);

  public R visit(ConditionalExpr n, A arg);

  public R visit(EnclosedExpr n, A arg);

  public R visit(FieldAccessExpr n, A arg);

  public R visit(InstanceOfExpr n, A arg);

  public R visit(StringLiteralExpr n, A arg);

  public R visit(IntegerLiteralExpr n, A arg);

  public R visit(LongLiteralExpr n, A arg);

  public R visit(IntegerLiteralMinValueExpr n, A arg);

  public R visit(LongLiteralMinValueExpr n, A arg);

  public R visit(CharLiteralExpr n, A arg);

  public R visit(DoubleLiteralExpr n, A arg);

  public R visit(BooleanLiteralExpr n, A arg);

  public R visit(NullLiteralExpr n, A arg);

  public R visit(MethodCallExpr n, A arg);

  public R visit(NameExpr n, A arg);

  public R visit(ObjectCreationExpr n, A arg);

  public R visit(QualifiedNameExpr n, A arg);

  public R visit(SuperMemberAccessExpr n, A arg);

  public R visit(ThisExpr n, A arg);

  public R visit(SuperExpr n, A arg);

  public R visit(UnaryExpr n, A arg);

  public R visit(VariableDeclarationExpr n, A arg);

  public R visit(MarkerAnnotationExpr n, A arg);

  public R visit(SingleMemberAnnotationExpr n, A arg);

  public R visit(NormalAnnotationExpr n, A arg);

  public R visit(MemberValuePair n, A arg);

  //- Statements ----------------------------------------

  public R visit(ExplicitConstructorInvocationStmt n, A arg);

  public R visit(TypeDeclarationStmt n, A arg);

  public R visit(AssertStmt n, A arg);

  public R visit(BlockStmt n, A arg);

  public R visit(LabeledStmt n, A arg);

  public R visit(EmptyStmt n, A arg);

  public R visit(ExpressionStmt n, A arg);

  public R visit(SwitchStmt n, A arg);

  public R visit(SwitchEntryStmt n, A arg);

  public R visit(BreakStmt n, A arg);

  public R visit(ReturnStmt n, A arg);

  public R visit(IfStmt n, A arg);

  public R visit(WhileStmt n, A arg);

  public R visit(ContinueStmt n, A arg);

  public R visit(DoStmt n, A arg);

  public R visit(ForeachStmt n, A arg);

  public R visit(ForStmt n, A arg);

  public R visit(ThrowStmt n, A arg);

  public R visit(SynchronizedStmt n, A arg);

  public R visit(TryStmt n, A arg);

  public R visit(CatchClause n, A arg);

}
