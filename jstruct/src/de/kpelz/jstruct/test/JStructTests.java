package de.kpelz.jstruct.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import de.kpelz.jstruct.main.JStruct;
import de.kpelz.jstruct.main.JStruct.JStructException;

/**
 * Copyright &#169 2018 Konstantin Pelz<br>
 * <br>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.<br>
 * <br>
 * JUnit tests
 */
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
			fail();
		} catch (JStructException e1) {
			try {
				invalidStruct = new JStruct("<");
				fail();
			} catch (JStructException e2) {
				try {
					invalidStruct = new JStruct(">");
					fail();
				} catch (JStructException e3) {
					try {
						invalidStruct = new JStruct("=");
						fail();
					} catch (JStructException e4) {
						try {
							invalidStruct = new JStruct("!");
							fail();
						} catch (JStructException e5) {
							try {
								invalidStruct = new JStruct("@");
								fail();
							} catch (JStructException e6) {
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void unpack_c_valuesTest() throws JStructException {
		// TODO fix test
		byte[] buffer = new byte[6];
		// a
		buffer[0] = Byte.valueOf("a");
		// b
		buffer[1] = Byte.valueOf("b");
		// c
		buffer[2] = Byte.valueOf("c");
		// x
		buffer[3] = Byte.valueOf("x");
		// y
		buffer[4] = Byte.valueOf("y");
		// z
		buffer[5] = Byte.valueOf("z");

		JStruct bStruct = new JStruct("cccccc");
		Object[] values = bStruct.unpack(buffer);

		assertEquals('B', (char) values[0]);
		assertEquals('a', (char) values[1]);
		assertEquals('a', (char) values[2]);
		assertEquals('a', (char) values[3]);
		assertEquals('a', (char) values[4]);
		assertEquals('a', (char) values[5]);

	}

	@Test
	public void unpack_b_valuesTest() throws JStructException {
		byte[] buffer = new byte[6];
		// 0000 0000
		buffer[0] = 0;
		// 1111 1111
		buffer[1] = (byte) ~(buffer[0]);
		// 0000 1111
		buffer[2] = (byte) (buffer[0] | 1 << 0 | 1 << 1 | 1 << 2 | 1 << 3);
		// 1111 0000
		buffer[3] = (byte) ~(buffer[2]);
		// 1000 0000
		buffer[4] = (byte) (buffer[0] | 1 << 7);
		// 0111 1111
		buffer[5] = (byte) ~(buffer[4]);

		JStruct bStruct = new JStruct("bbbbbb");
		Object[] values = bStruct.unpack(buffer);

		assertEquals(0, (int) values[0]);
		assertEquals(-1, (int) values[1]);
		assertEquals(15, (int) values[2]);
		assertEquals(-16, (int) values[3]);
		assertEquals(-128, (int) values[4]);
		assertEquals(127, (int) values[5]);
	}

	@Test
	public void unpack_B_valuesTest() throws JStructException {
		byte[] buffer = new byte[6];
		// 0000 0000
		buffer[0] = 0;
		// 1111 1111
		buffer[1] = (byte) ~(buffer[0]);
		// 0000 1111
		buffer[2] = (byte) (buffer[0] | 1 << 0 | 1 << 1 | 1 << 2 | 1 << 3);
		// 1111 0000
		buffer[3] = (byte) ~(buffer[2]);
		// 1000 0000
		buffer[4] = (byte) (buffer[0] | 1 << 7);
		// 0111 1111
		buffer[5] = (byte) ~(buffer[4]);

		JStruct BStruct = new JStruct("BBBBBB");
		Object[] values = BStruct.unpack(buffer);

		assertEquals(0, (int) values[0]);
		assertEquals(255, (int) values[1]);
		assertEquals(15, (int) values[2]);
		assertEquals(240, (int) values[3]);
		assertEquals(128, (int) values[4]);
		assertEquals(127, (int) values[5]);
	}

}
