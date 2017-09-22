package org.breder.java2js.java.node.body;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.TypeParameter;
import org.breder.java2js.java.node.expr.AnnotationExpr;
import org.breder.java2js.java.node.type.ClassOrInterfaceType;
import org.breder.java2js.java.node.type.ReferenceType;
import org.breder.java2js.java.node.type.Type;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class ClassOrInterfaceDeclaration extends TypeDeclaration {

  private final List<AnnotationExpr> annotations;

  private final boolean isInterface;

  private final List<TypeParameter> typeParameters;

  private final List<ClassOrInterfaceType> extendsList;

  private final List<ClassOrInterfaceType> implementsList;

  public ClassOrInterfaceDeclaration(int line, int column,
    JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations,
    boolean isInterface, String name, List<TypeParameter> typeParameters,
    List<ClassOrInterfaceType> extendsList,
    List<ClassOrInterfaceType> implementsList, List<BodyDeclaration> members) {
    super(line, column, javaDoc, name, modifiers, members);
    this.annotations = annotations;
    this.isInterface = isInterface;
    this.typeParameters = typeParameters;
    this.extendsList = extendsList;
    this.implementsList = implementsList;
  }

  public List<AnnotationExpr> getAnnotations() {
    return annotations;
  }

  public boolean isInterface() {
    return isInterface;
  }

  public List<TypeParameter> getTypeParameters() {
    return typeParameters;
  }

  public List<ClassOrInterfaceType> getExtends() {
    return extendsList;
  }

  public List<ClassOrInterfaceType> getImplements() {
    return implementsList;
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
    String packageName =
      output.getPackage().getName().toString().replace('.', '_');
    if (packageName.length() > 0) {
      packageName += '_';
    }
    List<FieldDeclaration> staticFields = new ArrayList<FieldDeclaration>();
    List<FieldDeclaration> noStaticFields = new ArrayList<FieldDeclaration>();
    List<ConstructorDeclaration> cMembers =
      new ArrayList<ConstructorDeclaration>();
    List<MethodDeclaration> mMembers = new ArrayList<MethodDeclaration>();
    for (BodyDeclaration member : this.getMembers()) {
      if (member instanceof FieldDeclaration) {
        FieldDeclaration fMember = (FieldDeclaration) member;
        if (Modifier.isStatic(fMember.getModifiers())) {
          staticFields.add(fMember);
        }
        else {
          noStaticFields.add(fMember);
        }
      }
      else if (member instanceof ConstructorDeclaration) {
        cMembers.add((ConstructorDeclaration) member);
      }
      else if (member instanceof MethodDeclaration) {
        mMembers.add((MethodDeclaration) member);
      }
    }
    output.write("function ");
    output.write(packageName);
    output.write(this.getName());
    output.write("() {");
    output.writeDebugLine(1);
    output.incDebugTab();
    for (FieldDeclaration fMember : noStaticFields) {
      for (VariableDeclarator vMember : fMember.getVariables()) {
        output.writeDebugTab();
        output.write("this.");
        output.write(vMember.getId().getName());
        output.write(" = ");
        if (vMember.getInit() == null) {
          output.write("null");
        }
        else {
          vMember.getInit().writeJs(output);
        }
        output.write(";");
        output.writeDebug("\n");
      }
    }
    output.decDebugTab();
    output.write("}");
    output.writeDebugLine(2);
    if (staticFields.size() > 0) {
      for (FieldDeclaration fMember : staticFields) {
        for (VariableDeclarator vMember : fMember.getVariables()) {
          output.write(packageName);
          output.write(this.getName());
          output.write(".prototype.");
          output.write(vMember.getId().getName());
          output.write(" = ");
          if (vMember.getInit() == null) {
            output.write("null");
          }
          else {
            vMember.getInit().writeJs(output);
          }
          output.write(";");
          output.writeDebugLine(1);
        }
      }
      output.writeDebugLine(2);
    }
    for (ConstructorDeclaration cMember : cMembers) {
      List<Parameter> parameters = cMember.getParameters();
      output.write(packageName);
      output.write(this.getName());
      output.write(".prototype.__init");
      writeParameters(output, parameters);
      output.write(" = function (");
      if (parameters != null) {
        for (int n = 0; n < parameters.size(); n++) {
          Parameter parameter = parameters.get(n);
          parameter.writeJs(output);
          if (n != parameters.size() - 1) {
            output.write(',');
            output.writeDebug(" ");
          }
        }
      }
      output.write(") {");
      output.writeDebugLine(1);
      cMember.writeJs(output);
      output.write("}");
      output.writeDebugLine(2);
    }
    for (MethodDeclaration mMember : mMembers) {
      List<Parameter> params = mMember.getParameters();
      output.write(packageName);
      output.write(this.getName());
      output.write(".prototype.");
      output.write(mMember.getName());
      writeParameters(output, params);
      output.write(" = function (");
      output.write(") ");
      if (mMember.getBody() == null) {
        output.write("{}");
      }
      else {
        mMember.getBody().writeJs(output);
      }
      output.writeDebugLine(2);
    }
  }

  public static void writeParameters(PrinterOutputStream output,
    List<Parameter> params) throws IOException {
    if (params != null && params.size() > 0) {
      for (Parameter param : params) {
        output.write("$");
        Type type = param.getType();
        if (type instanceof ClassOrInterfaceType) {
          ClassOrInterfaceType cType = (ClassOrInterfaceType) type;
          output.write(cType.getName());
        }
        else if (type instanceof ReferenceType) {
          ReferenceType rType = (ReferenceType) type;
          output.write(rType.toString());
        }
        else {
          output.write(type.toString());
        }
      }
    }
  }

}