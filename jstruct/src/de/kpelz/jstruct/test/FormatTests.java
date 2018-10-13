package de.kpelz.jstruct.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import de.kpelz.jstruct.main.JStruct;
import de.kpelz.jstruct.main.JStruct.JStructException;

public class FormatTests {

	@Test
	public void formatValidationTest() throws JStructException {
		new JStruct("<x");
		new JStruct(">x");
		new JStruct("=x");
		new JStruct("!x");
		new JStruct("42x");
		new JStruct("1x");
		new JStruct("x");
		new JStruct("c");
		new JStruct("b");
		new JStruct("B");
		new JStruct("?");
		new JStruct("h");
		new JStruct("H");
		new JStruct("i");
		new JStruct("I");
		new JStruct("l");
		new JStruct("L");
		new JStruct("q");
		// new JStruct("Q");
		// new JStruct("e");
		new JStruct("f");
		new JStruct("d");
		new JStruct("s");
		new JStruct("p");
		new JStruct("xcbB?hHiIlLqfdsp");
		try {
			new JStruct("");
			fail();
		} catch (JStructException e1) {
			try {
				new JStruct("<");
				fail();
			} catch (JStructException e2) {
				try {
					new JStruct(">");
					fail();
				} catch (JStructException e3) {
					try {
						new JStruct("=");
						fail();
					} catch (JStructException e4) {
						try {
							new JStruct("!");
							fail();
						} catch (JStructException e5) {
							try {
								new JStruct("@");
								fail();
							} catch (JStructException e6) {
							}
						}
					}
				}
			}
		}
	}

}
