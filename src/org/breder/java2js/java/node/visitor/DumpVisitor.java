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

import java.util.Iterator;
import java.util.List;

import org.breder.java2js.java.node.BlockComment;
import org.breder.java2js.java.node.CompilationUnit;
import org.breder.java2js.java.node.ImportDeclaration;
import org.breder.java2js.java.node.LineComment;
import org.breder.java2js.java.node.Node;
import org.breder.java2js.java.node.PackageDeclaration;
import org.breder.java2js.java.node.TypeParameter;
import org.breder.java2js.java.node.body.AnnotationDeclaration;
import org.breder.java2js.java.node.body.AnnotationMemberDeclaration;
import org.breder.java2js.java.node.body.BodyDeclaration;
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
import org.breder.java2js.java.node.body.ModifierSet;
import org.breder.java2js.java.node.body.Parameter;
import org.breder.java2js.java.node.body.TypeDeclaration;
import org.breder.java2js.java.node.body.VariableDeclarator;
import org.breder.java2js.java.node.body.VariableDeclaratorId;
import org.breder.java2js.java.node.expr.AnnotationExpr;
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
import org.breder.java2js.java.node.expr.Expression;
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
import org.breder.java2js.java.node.stmt.Statement;
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
import org.breder.java2js.java.node.type.Type;
import org.breder.java2js.java.node.type.VoidType;
import org.breder.java2js.java.node.type.WildcardType;

/**
 * @author Julio Vilmar Gesser
 */

public final class DumpVisitor implements VoidVisitor<Object> {

	private final SourcePrinter printer = new SourcePrinter();

	public String getSource() {
		return printer.getSource();
	}

	private void printModifiers(int modifiers) {
		if (ModifierSet.isPrivate(modifiers)) {
			printer.print("private ");
		}
		if (ModifierSet.isProtected(modifiers)) {
			printer.print("protected ");
		}
		if (ModifierSet.isPublic(modifiers)) {
			printer.print("public ");
		}
		if (ModifierSet.isAbstract(modifiers)) {
			printer.print("abstract ");
		}
		if (ModifierSet.isStatic(modifiers)) {
			printer.print("static ");
		}
		if (ModifierSet.isFinal(modifiers)) {
			printer.print("final ");
		}
		if (ModifierSet.isNative(modifiers)) {
			printer.print("native ");
		}
		if (ModifierSet.isStrictfp(modifiers)) {
			printer.print("strictfp ");
		}
		if (ModifierSet.isSynchronized(modifiers)) {
			printer.print("synchronized ");
		}
		if (ModifierSet.isTransient(modifiers)) {
			printer.print("transient ");
		}
		if (ModifierSet.isVolatile(modifiers)) {
			printer.print("volatile ");
		}
	}

	private void printMembers(List<BodyDeclaration> members, Object arg) {
		for (BodyDeclaration member : members) {
			printer.printLn();
			member.accept(this, arg);
			printer.printLn();
		}
	}

	private void printMemberAnnotations(List<AnnotationExpr> annotations,
			Object arg) {
		if (annotations != null) {
			for (AnnotationExpr a : annotations) {
				a.accept(this, arg);
				printer.printLn();
			}
		}
	}

	private void printAnnotations(List<AnnotationExpr> annotations, Object arg) {
		if (annotations != null) {
			for (AnnotationExpr a : annotations) {
				a.accept(this, arg);
				printer.print(" ");
			}
		}
	}

	private void printTypeArgs(List<Type> args, Object arg) {
		if (args != null) {
			printer.print("<");
			for (Iterator<Type> i = args.iterator(); i.hasNext();) {
				Type t = i.next();
				t.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
			printer.print(">");
		}
	}

	private void printTypeParameters(List<TypeParameter> args, Object arg) {
		if (args != null) {
			printer.print("<");
			for (Iterator<TypeParameter> i = args.iterator(); i.hasNext();) {
				TypeParameter t = i.next();
				t.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
			printer.print(">");
		}
	}

	public void visit(Node n, Object arg) {
		throw new IllegalStateException(n.getClass().getName());
	}

	public void visit(CompilationUnit n, Object arg) {
		if (n.getPakage() != null) {
			n.getPakage().accept(this, arg);
		}
		if (n.getImports() != null) {
			for (ImportDeclaration i : n.getImports()) {
				i.accept(this, arg);
			}
			printer.printLn();
		}
		if (n.getTypes() != null) {
			for (Iterator<TypeDeclaration> i = n.getTypes().iterator(); i
					.hasNext();) {
				i.next().accept(this, arg);
				printer.printLn();
				if (i.hasNext()) {
					printer.printLn();
				}
			}
		}
	}

	public void visit(PackageDeclaration n, Object arg) {
		printAnnotations(n.getAnnotations(), arg);
		printer.print("package ");
		n.getName().accept(this, arg);
		printer.printLn(";");
		printer.printLn();
	}

	public void visit(NameExpr n, Object arg) {
		printer.print(n.getName());
	}

	public void visit(QualifiedNameExpr n, Object arg) {
		n.getQualifier().accept(this, arg);
		printer.print(".");
		printer.print(n.getName());
	}

	public void visit(ImportDeclaration n, Object arg) {
		printer.print("import ");
		if (n.isStatic()) {
			printer.print("static ");
		}
		n.getName().accept(this, arg);
		if (n.isAsterisk()) {
			printer.print(".*");
		}
		printer.printLn(";");
	}

	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		if (n.isInterface()) {
			printer.print("interface ");
		} else {
			printer.print("class ");
		}

		printer.print(n.getName());

		printTypeParameters(n.getTypeParameters(), arg);

		if (n.getExtends() != null) {
			printer.print(" extends ");
			for (Iterator<ClassOrInterfaceType> i = n.getExtends().iterator(); i
					.hasNext();) {
				ClassOrInterfaceType c = i.next();
				c.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}

		if (n.getImplements() != null) {
			printer.print(" implements ");
			for (Iterator<ClassOrInterfaceType> i = n.getImplements()
					.iterator(); i.hasNext();) {
				ClassOrInterfaceType c = i.next();
				c.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}

		printer.printLn(" {");
		printer.indent();
		if (n.getMembers() != null) {
			printMembers(n.getMembers(), arg);
		}
		printer.unindent();
		printer.print("}");
	}

	public void visit(EmptyTypeDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printer.print(";");
	}

	public void visit(JavadocComment n, Object arg) {
		printer.print("/**");
		printer.print(n.getContent());
		printer.printLn("*/");
	}

	public void visit(ClassOrInterfaceType n, Object arg) {
		if (n.getScope() != null) {
			n.getScope().accept(this, arg);
			printer.print(".");
		}
		printer.print(n.getName());
		printTypeArgs(n.getTypeArgs(), arg);
	}

	public void visit(TypeParameter n, Object arg) {
		printer.print(n.getName());
		if (n.getTypeBound() != null) {
			printer.print(" extends ");
			for (Iterator<ClassOrInterfaceType> i = n.getTypeBound().iterator(); i
					.hasNext();) {
				ClassOrInterfaceType c = i.next();
				c.accept(this, arg);
				if (i.hasNext()) {
					printer.print(" & ");
				}
			}
		}
	}

	public void visit(PrimitiveType n, Object arg) {
		switch (n.getType()) {
		case Boolean:
			printer.print("boolean");
			break;
		case Byte:
			printer.print("byte");
			break;
		case Char:
			printer.print("char");
			break;
		case Double:
			printer.print("double");
			break;
		case Float:
			printer.print("float");
			break;
		case Int:
			printer.print("int");
			break;
		case Long:
			printer.print("long");
			break;
		case Short:
			printer.print("short");
			break;
		}
	}

	public void visit(ReferenceType n, Object arg) {
		n.getType().accept(this, arg);
		for (int i = 0; i < n.getArrayCount(); i++) {
			printer.print("[]");
		}
	}

	public void visit(WildcardType n, Object arg) {
		printer.print("?");
		if (n.getExtends() != null) {
			printer.print(" extends ");
			n.getExtends().accept(this, arg);
		}
		if (n.getSuper() != null) {
			printer.print(" super ");
			n.getSuper().accept(this, arg);
		}
	}

	public void visit(FieldDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());
		n.getType().accept(this, arg);

		printer.print(" ");
		for (Iterator<VariableDeclarator> i = n.getVariables().iterator(); i
				.hasNext();) {
			VariableDeclarator var = i.next();
			var.accept(this, arg);
			if (i.hasNext()) {
				printer.print(", ");
			}
		}

		printer.print(";");
	}

	public void visit(VariableDeclarator n, Object arg) {
		n.getId().accept(this, arg);
		if (n.getInit() != null) {
			printer.print(" = ");
			n.getInit().accept(this, arg);
		}
	}

	public void visit(VariableDeclaratorId n, Object arg) {
		printer.print(n.getName());
		for (int i = 0; i < n.getArrayCount(); i++) {
			printer.print("[]");
		}
	}

	public void visit(ArrayInitializerExpr n, Object arg) {
		printer.print("{");
		if (n.getValues() != null) {
			printer.print(" ");
			for (Iterator<Expression> i = n.getValues().iterator(); i.hasNext();) {
				Expression expr = i.next();
				expr.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
			printer.print(" ");
		}
		printer.print("}");
	}

	public void visit(VoidType n, Object arg) {
		printer.print("void");
	}

	public void visit(ArrayAccessExpr n, Object arg) {
		n.getName().accept(this, arg);
		printer.print("[");
		n.getIndex().accept(this, arg);
		printer.print("]");
	}

	public void visit(ArrayCreationExpr n, Object arg) {
		printer.print("new ");
		n.getType().accept(this, arg);
		printTypeArgs(n.getTypeArgs(), arg);

		if (n.getDimensions() != null) {
			for (Expression dim : n.getDimensions()) {
				printer.print("[");
				dim.accept(this, arg);
				printer.print("]");
			}
			for (int i = 0; i < n.getArrayCount(); i++) {
				printer.print("[]");
			}
		} else {
			for (int i = 0; i < n.getArrayCount(); i++) {
				printer.print("[]");
			}
			printer.print(" ");
			n.getInitializer().accept(this, arg);
		}
	}

	public void visit(AssignExpr n, Object arg) {
		n.getTarget().accept(this, arg);
		printer.print(" ");
		switch (n.getOperator()) {
		case assign:
			printer.print("=");
			break;
		case and:
			printer.print("&=");
			break;
		case or:
			printer.print("|=");
			break;
		case xor:
			printer.print("^=");
			break;
		case plus:
			printer.print("+=");
			break;
		case minus:
			printer.print("-=");
			break;
		case rem:
			printer.print("%=");
			break;
		case slash:
			printer.print("/=");
			break;
		case star:
			printer.print("*=");
			break;
		case lShift:
			printer.print("<<=");
			break;
		case rSignedShift:
			printer.print(">>=");
			break;
		case rUnsignedShift:
			printer.print(">>>=");
			break;
		}
		printer.print(" ");
		n.getValue().accept(this, arg);
	}

	public void visit(BinaryExpr n, Object arg) {
		n.getLeft().accept(this, arg);
		printer.print(" ");
		switch (n.getOperator()) {
		case or:
			printer.print("||");
			break;
		case and:
			printer.print("&&");
			break;
		case binOr:
			printer.print("|");
			break;
		case binAnd:
			printer.print("&");
			break;
		case xor:
			printer.print("^");
			break;
		case equals:
			printer.print("==");
			break;
		case notEquals:
			printer.print("!=");
			break;
		case less:
			printer.print("<");
			break;
		case greater:
			printer.print(">");
			break;
		case lessEquals:
			printer.print("<=");
			break;
		case greaterEquals:
			printer.print(">=");
			break;
		case lShift:
			printer.print("<<");
			break;
		case rSignedShift:
			printer.print(">>");
			break;
		case rUnsignedShift:
			printer.print(">>>");
			break;
		case plus:
			printer.print("+");
			break;
		case minus:
			printer.print("-");
			break;
		case times:
			printer.print("*");
			break;
		case divide:
			printer.print("/");
			break;
		case remainder:
			printer.print("%");
			break;
		}
		printer.print(" ");
		n.getRight().accept(this, arg);
	}

	public void visit(CastExpr n, Object arg) {
		printer.print("(");
		n.getType().accept(this, arg);
		printer.print(") ");
		n.getExpr().accept(this, arg);
	}

	public void visit(ClassExpr n, Object arg) {
		n.getType().accept(this, arg);
		printer.print(".class");
	}

	public void visit(ConditionalExpr n, Object arg) {
		n.getCondition().accept(this, arg);
		printer.print(" ? ");
		n.getThenExpr().accept(this, arg);
		printer.print(" : ");
		n.getElseExpr().accept(this, arg);
	}

	public void visit(EnclosedExpr n, Object arg) {
		printer.print("(");
		n.getInner().accept(this, arg);
		printer.print(")");
	}

	public void visit(FieldAccessExpr n, Object arg) {
		n.getScope().accept(this, arg);
		printer.print(".");
		printer.print(n.getField());
	}

	public void visit(InstanceOfExpr n, Object arg) {
		n.getExpr().accept(this, arg);
		printer.print(" instanceof ");
		n.getType().accept(this, arg);
	}

	public void visit(CharLiteralExpr n, Object arg) {
		printer.print("'");
		printer.print(n.getValue());
		printer.print("'");
	}

	public void visit(DoubleLiteralExpr n, Object arg) {
		printer.print(n.getValue());
	}

	public void visit(IntegerLiteralExpr n, Object arg) {
		printer.print(n.getValue());
	}

	public void visit(LongLiteralExpr n, Object arg) {
		printer.print(n.getValue());
	}

	public void visit(IntegerLiteralMinValueExpr n, Object arg) {
		printer.print(n.getValue());
	}

	public void visit(LongLiteralMinValueExpr n, Object arg) {
		printer.print(n.getValue());
	}

	public void visit(StringLiteralExpr n, Object arg) {
		printer.print("\"");
		printer.print(n.getValue());
		printer.print("\"");
	}

	public void visit(BooleanLiteralExpr n, Object arg) {
		printer.print(n.getValue().toString());
	}

	public void visit(NullLiteralExpr n, Object arg) {
		printer.print("null");
	}

	public void visit(ThisExpr n, Object arg) {
		if (n.getClassExpr() != null) {
			n.getClassExpr().accept(this, arg);
			printer.print(".");
		}
		printer.print("this");
	}

	public void visit(SuperExpr n, Object arg) {
		if (n.getClassExpr() != null) {
			n.getClassExpr().accept(this, arg);
			printer.print(".");
		}
		printer.print("super");
	}

	public void visit(MethodCallExpr n, Object arg) {
		if (n.getScope() != null) {
			n.getScope().accept(this, arg);
			printer.print(".");
		}
		printTypeArgs(n.getTypeArgs(), arg);
		printer.print(n.getName());
		printer.print("(");
		if (n.getArgs() != null) {
			for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print(")");
	}

	public void visit(ObjectCreationExpr n, Object arg) {
		if (n.getScope() != null) {
			n.getScope().accept(this, arg);
			printer.print(".");
		}

		printer.print("new ");

		printTypeArgs(n.getTypeArgs(), arg);
		n.getType().accept(this, arg);

		printer.print("(");
		if (n.getArgs() != null) {
			for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print(")");

		if (n.getAnonymousClassBody() != null) {
			printer.printLn(" {");
			printer.indent();
			printMembers(n.getAnonymousClassBody(), arg);
			printer.unindent();
			printer.print("}");
		}
	}

	public void visit(SuperMemberAccessExpr n, Object arg) {
		printer.print("super.");
		printer.print(n.getName());
	}

	public void visit(UnaryExpr n, Object arg) {
		switch (n.getOperator()) {
		case positive:
			printer.print("+");
			break;
		case negative:
			printer.print("-");
			break;
		case inverse:
			printer.print("~");
			break;
		case not:
			printer.print("!");
			break;
		case preIncrement:
			printer.print("++");
			break;
		case preDecrement:
			printer.print("--");
			break;
		}

		n.getExpr().accept(this, arg);

		switch (n.getOperator()) {
		case posIncrement:
			printer.print("++");
			break;
		case posDecrement:
			printer.print("--");
			break;
		}
	}

	public void visit(ConstructorDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		printTypeParameters(n.getTypeParameters(), arg);
		if (n.getTypeParameters() != null) {
			printer.print(" ");
		}
		printer.print(n.getName());

		printer.print("(");
		if (n.getParameters() != null) {
			for (Iterator<Parameter> i = n.getParameters().iterator(); i
					.hasNext();) {
				Parameter p = i.next();
				p.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print(")");

		if (n.getThrows() != null) {
			printer.print(" throws ");
			for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
				NameExpr name = i.next();
				name.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print(" ");
		n.getBlock().accept(this, arg);
	}

	public void visit(MethodDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		printTypeParameters(n.getTypeParameters(), arg);
		if (n.getTypeParameters() != null) {
			printer.print(" ");
		}

		n.getType().accept(this, arg);
		printer.print(" ");
		printer.print(n.getName());

		printer.print("(");
		if (n.getParameters() != null) {
			for (Iterator<Parameter> i = n.getParameters().iterator(); i
					.hasNext();) {
				Parameter p = i.next();
				p.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print(")");

		for (int i = 0; i < n.getArrayCount(); i++) {
			printer.print("[]");
		}

		if (n.getThrows() != null) {
			printer.print(" throws ");
			for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
				NameExpr name = i.next();
				name.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		if (n.getBody() == null) {
			printer.print(";");
		} else {
			printer.print(" ");
			n.getBody().accept(this, arg);
		}
	}

	public void visit(Parameter n, Object arg) {
		printAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		n.getType().accept(this, arg);
		if (n.isVarArgs()) {
			printer.print("...");
		}
		printer.print(" ");
		n.getId().accept(this, arg);
	}

	public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
		if (n.isThis()) {
			printTypeArgs(n.getTypeArgs(), arg);
			printer.print("this");
		} else {
			if (n.getExpr() != null) {
				n.getExpr().accept(this, arg);
				printer.print(".");
			}
			printTypeArgs(n.getTypeArgs(), arg);
			printer.print("super");
		}
		printer.print("(");
		if (n.getArgs() != null) {
			for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print(");");
	}

	public void visit(VariableDeclarationExpr n, Object arg) {
		printAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		n.getType().accept(this, arg);
		printer.print(" ");

		for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i
				.hasNext();) {
			VariableDeclarator v = i.next();
			v.accept(this, arg);
			if (i.hasNext()) {
				printer.print(", ");
			}
		}
	}

	public void visit(TypeDeclarationStmt n, Object arg) {
		n.getTypeDeclaration().accept(this, arg);
	}

	public void visit(AssertStmt n, Object arg) {
		printer.print("assert ");
		n.getCheck().accept(this, arg);
		if (n.getMessage() != null) {
			printer.print(" : ");
			n.getMessage().accept(this, arg);
		}
		printer.print(";");
	}

	public void visit(BlockStmt n, Object arg) {
		printer.printLn("{");
		if (n.getStmts() != null) {
			printer.indent();
			for (Statement s : n.getStmts()) {
				s.accept(this, arg);
				printer.printLn();
			}
			printer.unindent();
		}
		printer.print("}");

	}

	public void visit(LabeledStmt n, Object arg) {
		printer.print(n.getLabel());
		printer.print(": ");
		n.getStmt().accept(this, arg);
	}

	public void visit(EmptyStmt n, Object arg) {
		printer.print(";");
	}

	public void visit(ExpressionStmt n, Object arg) {
		n.getExpression().accept(this, arg);
		printer.print(";");
	}

	public void visit(SwitchStmt n, Object arg) {
		printer.print("switch(");
		n.getSelector().accept(this, arg);
		printer.printLn(") {");
		if (n.getEntries() != null) {
			printer.indent();
			for (SwitchEntryStmt e : n.getEntries()) {
				e.accept(this, arg);
			}
			printer.unindent();
		}
		printer.print("}");

	}

	public void visit(SwitchEntryStmt n, Object arg) {
		if (n.getLabel() != null) {
			printer.print("case ");
			n.getLabel().accept(this, arg);
			printer.print(":");
		} else {
			printer.print("default:");
		}
		printer.printLn();
		printer.indent();
		if (n.getStmts() != null) {
			for (Statement s : n.getStmts()) {
				s.accept(this, arg);
				printer.printLn();
			}
		}
		printer.unindent();
	}

	public void visit(BreakStmt n, Object arg) {
		printer.print("break");
		if (n.getId() != null) {
			printer.print(" ");
			printer.print(n.getId());
		}
		printer.print(";");
	}

	public void visit(ReturnStmt n, Object arg) {
		printer.print("return");
		if (n.getExpr() != null) {
			printer.print(" ");
			n.getExpr().accept(this, arg);
		}
		printer.print(";");
	}

	public void visit(EnumDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		printer.print("enum ");
		printer.print(n.getName());

		if (n.getImplements() != null) {
			printer.print(" implements ");
			for (Iterator<ClassOrInterfaceType> i = n.getImplements()
					.iterator(); i.hasNext();) {
				ClassOrInterfaceType c = i.next();
				c.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}

		printer.printLn(" {");
		printer.indent();
		if (n.getEntries() != null) {
			printer.printLn();
			for (Iterator<EnumConstantDeclaration> i = n.getEntries()
					.iterator(); i.hasNext();) {
				EnumConstantDeclaration e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		if (n.getMembers() != null) {
			printer.printLn(";");
			printMembers(n.getMembers(), arg);
		} else {
			if (n.getEntries() != null) {
				printer.printLn();
			}
		}
		printer.unindent();
		printer.print("}");
	}

	public void visit(EnumConstantDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printer.print(n.getName());

		if (n.getArgs() != null) {
			printer.print("(");
			for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
			printer.print(")");
		}

		if (n.getClassBody() != null) {
			printer.printLn(" {");
			printer.indent();
			printMembers(n.getClassBody(), arg);
			printer.unindent();
			printer.printLn("}");
		}
	}

	public void visit(EmptyMemberDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printer.print(";");
	}

	public void visit(InitializerDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.isStatic()) {
			printer.print("static ");
		}
		n.getBlock().accept(this, arg);
	}

	public void visit(IfStmt n, Object arg) {
		printer.print("if (");
		n.getCondition().accept(this, arg);
		printer.print(") ");
		n.getThenStmt().accept(this, arg);
		if (n.getElseStmt() != null) {
			printer.print(" else ");
			n.getElseStmt().accept(this, arg);
		}
	}

	public void visit(WhileStmt n, Object arg) {
		printer.print("while (");
		n.getCondition().accept(this, arg);
		printer.print(") ");
		n.getBody().accept(this, arg);
	}

	public void visit(ContinueStmt n, Object arg) {
		printer.print("continue");
		if (n.getId() != null) {
			printer.print(" ");
			printer.print(n.getId());
		}
		printer.print(";");
	}

	public void visit(DoStmt n, Object arg) {
		printer.print("do ");
		n.getBody().accept(this, arg);
		printer.print(" while (");
		n.getCondition().accept(this, arg);
		printer.print(");");
	}

	public void visit(ForeachStmt n, Object arg) {
		printer.print("for (");
		n.getVariable().accept(this, arg);
		printer.print(" : ");
		n.getIterable().accept(this, arg);
		printer.print(") ");
		n.getBody().accept(this, arg);
	}

	public void visit(ForStmt n, Object arg) {
		printer.print("for (");
		if (n.getInit() != null) {
			for (Iterator<Expression> i = n.getInit().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print("; ");
		if (n.getCompare() != null) {
			n.getCompare().accept(this, arg);
		}
		printer.print("; ");
		if (n.getUpdate() != null) {
			for (Iterator<Expression> i = n.getUpdate().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print(") ");
		n.getBody().accept(this, arg);
	}

	public void visit(ThrowStmt n, Object arg) {
		printer.print("throw ");
		n.getExpr().accept(this, arg);
		printer.print(";");
	}

	public void visit(SynchronizedStmt n, Object arg) {
		printer.print("synchronized (");
		n.getExpr().accept(this, arg);
		printer.print(") ");
		n.getBlock().accept(this, arg);
	}

	public void visit(TryStmt n, Object arg) {
		printer.print("try ");
		n.getTryBlock().accept(this, arg);
		if (n.getCatchs() != null) {
			for (CatchClause c : n.getCatchs()) {
				c.accept(this, arg);
			}
		}
		if (n.getFinallyBlock() != null) {
			printer.print(" finally ");
			n.getFinallyBlock().accept(this, arg);
		}
	}

	public void visit(CatchClause n, Object arg) {
		printer.print(" catch (");
		n.getExcept().accept(this, arg);
		printer.print(") ");
		n.getCatchBlock().accept(this, arg);

	}

	public void visit(AnnotationDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		printer.print("@interface ");
		printer.print(n.getName());
		printer.printLn(" {");
		printer.indent();
		if (n.getMembers() != null) {
			printMembers(n.getMembers(), arg);
		}
		printer.unindent();
		printer.print("}");
	}

	public void visit(AnnotationMemberDeclaration n, Object arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		printMemberAnnotations(n.getAnnotations(), arg);
		printModifiers(n.getModifiers());

		n.getType().accept(this, arg);
		printer.print(" ");
		printer.print(n.getName());
		printer.print("()");
		if (n.getDefaultValue() != null) {
			printer.print(" default ");
			n.getDefaultValue().accept(this, arg);
		}
		printer.print(";");
	}

	public void visit(MarkerAnnotationExpr n, Object arg) {
		printer.print("@");
		n.getName().accept(this, arg);
	}

	public void visit(SingleMemberAnnotationExpr n, Object arg) {
		printer.print("@");
		n.getName().accept(this, arg);
		printer.print("(");
		n.getMemberValue().accept(this, arg);
		printer.print(")");
	}

	public void visit(NormalAnnotationExpr n, Object arg) {
		printer.print("@");
		n.getName().accept(this, arg);
		printer.print("(");
		for (Iterator<MemberValuePair> i = n.getPairs().iterator(); i.hasNext();) {
			MemberValuePair m = i.next();
			m.accept(this, arg);
			if (i.hasNext()) {
				printer.print(", ");
			}
		}
		printer.print(")");
	}

	public void visit(MemberValuePair n, Object arg) {
		printer.print(n.getName());
		printer.print(" = ");
		n.getValue().accept(this, arg);
	}

	public void visit(LineComment n, Object arg) {
		printer.print("//");
		printer.printLn(n.getContent());
	}

	public void visit(BlockComment n, Object arg) {
		printer.print("/*");
		printer.print(n.getContent());
		printer.printLn("*/");
	}

}
