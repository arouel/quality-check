package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Date;
import java.util.List;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class ImportsTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Imports.class, areImmutable(), provided(ImmutableList.class).isAlsoImmutable());
	}

	@Test
	public void filter_java_lang() {
		assertEquals(0, Imports.of(Import.of(String.class)).filter().asList().size());
		assertEquals(0, Imports.of(Import.of(new Type("String"))).filter().asList().size());
		assertEquals(0, Imports.of(Import.of(new Type("java.lang.String"))).filter().asList().size());
	}

	@Test
	public void find() {
		final List<Import> set = ImmutableList.of(Import.of(String.class), Import.of(Date.class), Import.of(Integer.class));
		final Imports imports = Imports.copyOf(set);
		assertEquals(Import.of(String.class), imports.find("String"));
		assertEquals(Import.of(String.class), imports.find("java.lang.String"));
		assertEquals(Import.of(Date.class), imports.find("Date"));
		assertEquals(Import.of(Date.class), imports.find("java.util.Date"));
		assertEquals(Import.of(Integer.class), imports.find("Integer"));
		assertEquals(Import.of(Integer.class), imports.find("java.lang.Integer"));
	}

	@Test
	public void find_StringClass_withoutExplicitImport() {
		final List<Import> set = ImmutableList.of(Import.of(Date.class), Import.of(Integer.class));
		final Imports imports = Imports.copyOf(set);
		assertEquals(Import.of(String.class), imports.find("String"));
		assertEquals(Import.of(String.class), imports.find("java.lang.String"));
		assertEquals(Import.of(Date.class), imports.find("Date"));
		assertEquals(Import.of(Date.class), imports.find("java.util.Date"));
		assertEquals(Import.of(Integer.class), imports.find("Integer"));
		assertEquals(Import.of(Integer.class), imports.find("java.lang.Integer"));
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void find_typeName_isEmpty() {
		Imports.of(Import.of(String.class)).find("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void find_typeName_isNull() {
		Imports.of(Import.of(String.class)).find(null);
	}

	@Test
	public void find_typeName_notFound() {
		assertNull(Imports.of(Import.of(String.class)).find("Unknown"));
	}

}
