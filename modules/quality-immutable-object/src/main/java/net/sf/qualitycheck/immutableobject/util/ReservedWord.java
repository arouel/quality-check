package net.sf.qualitycheck.immutableobject.util;

import net.sf.qualitycheck.Check;

public enum ReservedWord {

	ABSTRACT("abstract"),

	ASSERT("assert"),

	BOOLEAN("boolean"),

	BREAK("break"),

	BYTE("byte"),

	CASE("case"),

	CATCH("catch"),

	CHAR("char"),

	CLASS("class"),

	CONST("const"),

	CONTINUE("continue"),

	DEFAULT("default"),

	DO("do"),

	DOUBLE("double"),

	ELSE("else"),

	ENUM("enum"),

	EXTENDS("extends"),

	FINAL("final"),

	FINALLY("finally"),

	FLOAT("float"),

	FOR("for"),

	GOTO("goto"),

	IF("if"),

	IMPLEMENTS("implements"),

	IMPORT("import"),

	INSTANCEOF("instanceof"),

	INT("int"),

	INTERFACE("interface"),

	LONG("long"),

	NATIVE("native"),

	NEW("new"),

	PACKAGE("package"),

	PRIVATE("private"),

	PROTECTED("protected"),

	PUBLIC("public"),

	RETURN("return"),

	SHORT("short"),

	STATIC("static"),

	STRICTFP("strictfp"),

	SUPER("super"),

	SWITCH("switch"),

	SYNCHRONIZED("synchronized"),

	THIS("this"),

	THROW("throw"),

	THROWS("throws"),

	TRANSIENT("transient"),

	TRY("try"),

	VOID("void"),

	VOLATILE("volatile"),

	WHILE("while");

	public static boolean isReserved(final String word) {
		Check.notNull(word, "word");
		boolean ret = false;
		for (final ReservedWord w : values()) {
			if (w._word.equals(word)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	private final String _word;

	ReservedWord(final String word) {
		_word = word;
	}

	public String getWord() {
		return _word;
	}

}
