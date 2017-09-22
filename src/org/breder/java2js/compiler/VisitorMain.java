package org.breder.java2js.compiler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.breder.java2js.java.JavaParser;
import org.breder.java2js.java.ParseException;
import org.breder.java2js.java.node.CompilationUnit;
import org.breder.java2js.java.node.visitor.DumpVisitor;

public class VisitorMain {

	public static void main(String[] args) throws Exception {
		CompilationUnit unit = compile();
		DumpVisitor visitor = new DumpVisitor();
		visitor.visit(unit, null);
		System.out.println(visitor.getSource());
	}

	private static CompilationUnit compile() throws FileNotFoundException,
			ParseException, IOException {
		FileInputStream in = new FileInputStream("src/"
				+ DumpVisitor.class.getName().replace('.', '/') + ".java");
		try {
			return new JavaParser(in).CompilationUnit();
		} finally {
			in.close();
		}
	}

}
