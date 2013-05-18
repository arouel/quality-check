package net.sf.qualitycheck.immutableobject.domain;

import net.sf.qualitycheck.Check;

public enum Primitive {

	BOOLEAN(ReservedWord.BOOLEAN.getWord()),

	BYTE(ReservedWord.BYTE.getWord()),

	LONG(ReservedWord.LONG.getWord()),

	INT(ReservedWord.INT.getWord()),

	DOUBLE(ReservedWord.DOUBLE.getWord()),

	FLOAT(ReservedWord.FLOAT.getWord()),

	CHAR(ReservedWord.CHAR.getWord()),

	SHORT(ReservedWord.SHORT.getWord());

	public static boolean isPrimitive(final String name) {
		Check.notNull(name, "name");
		boolean ret = false;
		for (final Primitive p : values()) {
			if (p.name.equals(name)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	private final String name;

	private Primitive(final String name) {
		this.name = Check.notNull(name);
	}

	public String getName() {
		return name;
	}

}
