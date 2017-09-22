package org.breder.java2js.java.node.expr;

import java.io.IOException;
import java.util.List;

import org.breder.java2js.compiler.PrinterOutputStream;
import org.breder.java2js.java.node.body.BodyDeclaration;
import org.breder.java2js.java.node.type.ClassOrInterfaceType;
import org.breder.java2js.java.node.type.Type;
import org.breder.java2js.java.node.visitor.GenericVisitor;
import org.breder.java2js.java.node.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class ObjectCreationExpr extends Expression {

  private final Expression scope;

  private final ClassOrInterfaceType type;

  private final List<Type> typeArgs;

  private final List<Expression> args;

  private final List<BodyDeclaration> anonymousClassBody;

  public ObjectCreationExpr(int line, int column, Expression scope,
    ClassOrInterfaceType type, List<Type> typeArgs, List<Expression> args,
    List<BodyDeclaration> anonymousBody) {
    super(line, column);
    this.scope = scope;
    this.type = type;
    this.typeArgs = typeArgs;
    this.args = args;
    this.anonymousClassBody = anonymousBody;
  }

  public Expression getScope() {
    return scope;
  }

  public ClassOrInterfaceType getType() {
    return type;
  }

  public List<Type> getTypeArgs() {
    return typeArgs;
  }

  public List<Expression> getArgs() {
    return args;
  }

  public List<BodyDeclaration> getAnonymousClassBody() {
    return anonymousClassBody;
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
    output.write("new ");
    output.write(type.getName());
    output.write("().__init");
    output.write('(');
    if (this.args != null) {
      for (int n = 0; n < this.args.size(); n++) {
        Expression arg = this.args.get(n);
        arg.writeJs(output);
        if (n != this.args.size() - 1) {
          output.write(',');
          output.writeDebug(" ");
        }
      }
    }
    output.write(')');
  }

}
