package de.kpelz.jstruct.test;

import org.junit.Test;

import de.kpelz.jstruct.main.JStruct;
import de.kpelz.jstruct.main.JStruct.JStructException;

public class JStructTests {

	@Test
	public void formatValidationTest() throws JStructException {
		JStruct validJStruct = new JStruct("<x");
		validJStruct = new JStruct(">x");
		validJStruct = new JStruct("=x");
		validJStruct = new JStruct("!x");
		validJStruct = new JStruct("42x");
		validJStruct = new JStruct("1x");
		validJStruct = new JStruct("x");
		validJStruct = new JStruct("c");
		validJStruct = new JStruct("b");
		validJStruct = new JStruct("B");
		validJStruct = new JStruct("?");
		validJStruct = new JStruct("h");
		validJStruct = new JStruct("H");
		validJStruct = new JStruct("i");
		validJStruct = new JStruct("I");
		validJStruct = new JStruct("l");
		validJStruct = new JStruct("L");
		validJStruct = new JStruct("q");
		// validJStruct = new JStruct("Q");
		// validJStruct = new JStruct("e");
		validJStruct = new JStruct("f");
		validJStruct = new JStruct("d");
		validJStruct = new JStruct("s");
		validJStruct = new JStruct("p");
		validJStruct = new JStruct("xcbB?hHiIlLqfdsp");
		JStruct invalidStruct;
		try {
			invalidStruct = new JStruct("");
		} catch (JStructException e) {
		} finally {
			try {
				invalidStruct = new JStruct("<");
			} catch (JStructException e) {
			} finally {
				try {
					invalidStruct = new JStruct(">");
				} catch (JStructException e) {
				} finally {
					try {
						invalidStruct = new JStruct("=");
					} catch (JStructException e) {
					} finally {
						try {
							invalidStruct = new JStruct("!");
						} catch (JStructException e) {
						} finally {
							try {
								invalidStruct = new JStruct("@");
							} catch (JStructException e) {
							} finally {
								try {
									invalidStruct = new JStruct("@x");
								} catch (JStructException e) {
								}
							}
						}
					}
				}
			}
		}
	}

}
