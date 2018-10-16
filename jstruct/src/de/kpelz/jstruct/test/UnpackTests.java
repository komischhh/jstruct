package de.kpelz.jstruct.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
 * <b>JUnit tests</b><br>
 * <br>
 * For unpacking the bytes must be inserted into the buffer in reverse
 * order.<br>
 */
public class UnpackTests {

	// 0000 0000
	public static final byte OOOO_OOOO = 0;

	// 1111 1111
	public static final byte IIII_IIII = (byte) (~OOOO_OOOO);

	// 1000 00000
	public static final byte IOOO_OOOO = (byte) (OOOO_OOOO | 1 << 7);

	// 0111 1111
	public static final byte OIII_IIII = (byte) (~IOOO_OOOO);

	// 0000 0001
	public static final byte OOOO_OOOI = (byte) (OOOO_OOOO | 1 << 0);

	// 1111 1110
	public static final byte IIII_IIIO = (byte) ~OOOO_OOOI;

	// 1010 1010
	public static final byte IOIO_IOIO =
			(byte) (IOOO_OOOO | 1 << 1 | 1 << 3 | 1 << 5);

	// 0101 0101
	public static final byte OIOI_OIOI = (byte) (~IOIO_IOIO);

	// 1111 0000
	public static final byte IIII_OOOO =
			(byte) (IOOO_OOOO | 1 << 4 | 1 << 5 | 1 << 6);

	// 0000 1111
	public static final byte OOOO_IIII = (byte) (~IIII_OOOO);

	public void unpack_c_valuesTest() throws JStructException {
		// TODO fix test
		byte[] buffer = new byte[12];
		buffer[0] = 72;
		buffer[1] = 101;
		buffer[2] = 108;
		buffer[3] = 108;
		buffer[4] = 111;
		buffer[5] = 32;
		buffer[6] = 87;
		buffer[7] = 111;
		buffer[8] = 114;
		buffer[9] = 108;
		buffer[10] = 100;
		buffer[11] = 33;

		JStruct struct = new JStruct("12c");
		Object[] values = struct.unpack(buffer);
		assertEquals(12, values.length);

		assertEquals('H', (char) values[0]);
		assertEquals('e', (char) values[1]);
		assertEquals('l', (char) values[2]);
		assertEquals('l', (char) values[3]);
		assertEquals('o', (char) values[4]);
		assertEquals(' ', (char) values[5]);
		assertEquals('W', (char) values[6]);
		assertEquals('o', (char) values[7]);
		assertEquals('r', (char) values[8]);
		assertEquals('l', (char) values[9]);
		assertEquals('d', (char) values[10]);
		assertEquals('!', (char) values[11]);
	}

	@Test
	public void unpack_b_valuesTest() throws JStructException {
		byte[] buffer = new byte[6];
		// 0000 0000
		buffer[0] = OOOO_OOOO;
		// 1111 1111
		buffer[1] = IIII_IIII;
		// 0000 1111
		buffer[2] = OOOO_IIII;
		// 1111 0000
		buffer[3] = IIII_OOOO;
		// 1000 0000
		buffer[4] = IOOO_OOOO;
		// 0111 1111
		buffer[5] = OIII_IIII;

		JStruct struct = new JStruct("6b");
		Object[] values = struct.unpack(buffer);
		assertEquals(6, values.length);

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
		buffer[0] = OOOO_OOOO;
		buffer[1] = IIII_IIII;
		buffer[2] = OOOO_IIII;
		buffer[3] = IIII_OOOO;
		buffer[4] = IOOO_OOOO;
		buffer[5] = OIII_IIII;

		JStruct struct = new JStruct("6B");
		Object[] values = struct.unpack(buffer);
		assertEquals(6, values.length);

		assertEquals(0, (int) values[0]);
		assertEquals(255, (int) values[1]);
		assertEquals(15, (int) values[2]);
		assertEquals(240, (int) values[3]);
		assertEquals(128, (int) values[4]);
		assertEquals(127, (int) values[5]);
	}

	@Test
	public void unpack_h_valuesTest() throws JStructException {
		byte[] buffer = new byte[16];
		// 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		// 1111 1111 1111 1111
		buffer[2] = IIII_IIII;
		buffer[3] = IIII_IIII;
		// 0000 1111 0000 1111
		buffer[4] = OOOO_IIII;
		buffer[5] = OOOO_IIII;
		// 1111 0000 1111 0000;
		buffer[6] = IIII_OOOO;
		buffer[7] = IIII_OOOO;
		// 1010 1010 1010 1010
		buffer[8] = IOIO_IOIO;
		buffer[9] = IOIO_IOIO;
		// 0101 0101 0101 0101;
		buffer[10] = OIOI_OIOI;
		buffer[11] = OIOI_OIOI;
		// 1000 0000 0000 0000
		buffer[12] = OOOO_OOOO;
		buffer[13] = IOOO_OOOO;
		// 0000 0000 0000 0001
		buffer[14] = OOOO_OOOI;
		buffer[15] = OOOO_OOOO;

		JStruct struct = new JStruct("8h");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0, values[0]);
		assertEquals(-1, values[1]);
		assertEquals(3855, values[2]);
		assertEquals(-3856, values[3]);
		assertEquals(-21846, values[4]);
		assertEquals(21845, values[5]);
		assertEquals(-32768, values[6]);
		assertEquals(1, values[7]);
	}

	@Test
	public void unpack_H_valuesTest() throws JStructException {
		byte[] buffer = new byte[16];
		// 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		// 1111 1111 1111 1111
		buffer[2] = IIII_IIII;
		buffer[3] = IIII_IIII;
		// 0000 1111 0000 1111
		buffer[4] = OOOO_IIII;
		buffer[5] = OOOO_IIII;
		// 1111 0000 1111 0000;
		buffer[6] = IIII_OOOO;
		buffer[7] = IIII_OOOO;
		// 1010 1010 1010 1010
		buffer[8] = IOIO_IOIO;
		buffer[9] = IOIO_IOIO;
		// 0101 0101 0101 0101;
		buffer[10] = OIOI_OIOI;
		buffer[11] = OIOI_OIOI;
		// 1000 0000 0000 0000
		buffer[12] = OOOO_OOOO;
		buffer[13] = IOOO_OOOO;
		// 0000 0000 0000 0001
		buffer[14] = OOOO_OOOI;
		buffer[15] = OOOO_OOOO;

		JStruct struct = new JStruct("8H");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0, values[0]);
		assertEquals(65535, values[1]);
		assertEquals(3855, values[2]);
		assertEquals(61680, values[3]);
		assertEquals(43690, values[4]);
		assertEquals(21845, values[5]);
		assertEquals(32768, values[6]);
		assertEquals(1, values[7]);
	}

	@Test
	public void unpack_i_valuesTest() throws JStructException {
		byte[] buffer = new byte[32];
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		buffer[2] = OOOO_OOOO;
		buffer[3] = OOOO_OOOO;
		// 1111 1111 1111 1111 1111 1111 1111 1111
		buffer[4] = IIII_IIII;
		buffer[5] = IIII_IIII;
		buffer[6] = IIII_IIII;
		buffer[7] = IIII_IIII;
		// 0000 1111 0000 1111 0000 1111 0000 1111
		buffer[8] = OOOO_IIII;
		buffer[9] = OOOO_IIII;
		buffer[10] = OOOO_IIII;
		buffer[11] = OOOO_IIII;
		// 1111 0000 1111 0000 1111 0000 1111 0000
		buffer[12] = IIII_OOOO;
		buffer[13] = IIII_OOOO;
		buffer[14] = IIII_OOOO;
		buffer[15] = IIII_OOOO;
		// 0101 0101 0101 0101 0101 0101 0101 0101
		buffer[16] = OIOI_OIOI;
		buffer[17] = OIOI_OIOI;
		buffer[18] = OIOI_OIOI;
		buffer[19] = OIOI_OIOI;
		// 1010 1010 1010 1010 1010 1010 1010 1010
		buffer[20] = IOIO_IOIO;
		buffer[21] = IOIO_IOIO;
		buffer[22] = IOIO_IOIO;
		buffer[23] = IOIO_IOIO;
		// 1000 0000 0000 0000 0000 0000 0000 0000
		buffer[24] = OOOO_OOOO;
		buffer[25] = OOOO_OOOO;
		buffer[26] = OOOO_OOOO;
		buffer[27] = IOOO_OOOO;
		// 0000 0000 0000 0000 0000 0000 0000 0001
		buffer[28] = OOOO_OOOI;
		buffer[29] = OOOO_OOOO;
		buffer[30] = OOOO_OOOO;
		buffer[31] = OOOO_OOOO;

		JStruct struct = new JStruct("8i");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0, values[0]);
		assertEquals(-1, values[1]);
		assertEquals(252645135, values[2]);
		assertEquals(-252645136, values[3]);
		assertEquals(1431655765, values[4]);
		assertEquals(-1431655766, values[5]);
		assertEquals(-2147483648, values[6]);
		assertEquals(1, values[7]);
	}

	@Test
	public void unpack_I_valuesTest() throws JStructException {
		byte[] buffer = new byte[32];
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		buffer[2] = OOOO_OOOO;
		buffer[3] = OOOO_OOOO;
		// 1111 1111 1111 1111 1111 1111 1111 1111
		buffer[4] = IIII_IIII;
		buffer[5] = IIII_IIII;
		buffer[6] = IIII_IIII;
		buffer[7] = IIII_IIII;
		// 0000 1111 0000 1111 0000 1111 0000 1111
		buffer[8] = OOOO_IIII;
		buffer[9] = OOOO_IIII;
		buffer[10] = OOOO_IIII;
		buffer[11] = OOOO_IIII;
		// 1111 0000 1111 0000 1111 0000 1111 0000
		buffer[12] = IIII_OOOO;
		buffer[13] = IIII_OOOO;
		buffer[14] = IIII_OOOO;
		buffer[15] = IIII_OOOO;
		// 0101 0101 0101 0101 0101 0101 0101 0101
		buffer[16] = OIOI_OIOI;
		buffer[17] = OIOI_OIOI;
		buffer[18] = OIOI_OIOI;
		buffer[19] = OIOI_OIOI;
		// 1010 1010 1010 1010 1010 1010 1010 1010
		buffer[20] = IOIO_IOIO;
		buffer[21] = IOIO_IOIO;
		buffer[22] = IOIO_IOIO;
		buffer[23] = IOIO_IOIO;
		// 1000 0000 0000 0000 0000 0000 0000 0000
		buffer[24] = OOOO_OOOO;
		buffer[25] = OOOO_OOOO;
		buffer[26] = OOOO_OOOO;
		buffer[27] = IOOO_OOOO;
		// 0000 0000 0000 0000 0000 0000 0000 0001
		buffer[28] = OOOO_OOOI;
		buffer[29] = OOOO_OOOO;
		buffer[30] = OOOO_OOOO;
		buffer[31] = OOOO_OOOO;

		JStruct struct = new JStruct("8I");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0L, values[0]);
		assertEquals(4294967295L, values[1]);
		assertEquals(252645135L, values[2]);
		assertEquals(4042322160L, values[3]);
		assertEquals(1431655765L, values[4]);
		assertEquals(2863311530L, values[5]);
		assertEquals(2147483648L, values[6]);
		assertEquals(1L, values[7]);
	}

	@Test
	public void unpack_l_valuesTest() throws JStructException {
		byte[] buffer = new byte[32];
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		buffer[2] = OOOO_OOOO;
		buffer[3] = OOOO_OOOO;
		// 1111 1111 1111 1111 1111 1111 1111 1111
		buffer[4] = IIII_IIII;
		buffer[5] = IIII_IIII;
		buffer[6] = IIII_IIII;
		buffer[7] = IIII_IIII;
		// 0000 1111 0000 1111 0000 1111 0000 1111
		buffer[8] = OOOO_IIII;
		buffer[9] = OOOO_IIII;
		buffer[10] = OOOO_IIII;
		buffer[11] = OOOO_IIII;
		// 1111 0000 1111 0000 1111 0000 1111 0000
		buffer[12] = IIII_OOOO;
		buffer[13] = IIII_OOOO;
		buffer[14] = IIII_OOOO;
		buffer[15] = IIII_OOOO;
		// 0101 0101 0101 0101 0101 0101 0101 0101
		buffer[16] = OIOI_OIOI;
		buffer[17] = OIOI_OIOI;
		buffer[18] = OIOI_OIOI;
		buffer[19] = OIOI_OIOI;
		// 1010 1010 1010 1010 1010 1010 1010 1010
		buffer[20] = IOIO_IOIO;
		buffer[21] = IOIO_IOIO;
		buffer[22] = IOIO_IOIO;
		buffer[23] = IOIO_IOIO;
		// 1000 0000 0000 0000 0000 0000 0000 0000
		buffer[24] = OOOO_OOOO;
		buffer[25] = OOOO_OOOO;
		buffer[26] = OOOO_OOOO;
		buffer[27] = IOOO_OOOO;
		// 0000 0000 0000 0000 0000 0000 0000 0001
		buffer[28] = OOOO_OOOI;
		buffer[29] = OOOO_OOOO;
		buffer[30] = OOOO_OOOO;
		buffer[31] = OOOO_OOOO;

		JStruct struct = new JStruct("8l");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0, values[0]);
		assertEquals(-1, values[1]);
		assertEquals(252645135, values[2]);
		assertEquals(-252645136, values[3]);
		assertEquals(1431655765, values[4]);
		assertEquals(-1431655766, values[5]);
		assertEquals(-2147483648, values[6]);
		assertEquals(1, values[7]);
	}

	@Test
	public void unpack_L_valuesTest() throws JStructException {
		byte[] buffer = new byte[32];
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		buffer[2] = OOOO_OOOO;
		buffer[3] = OOOO_OOOO;
		// 1111 1111 1111 1111 1111 1111 1111 1111
		buffer[4] = IIII_IIII;
		buffer[5] = IIII_IIII;
		buffer[6] = IIII_IIII;
		buffer[7] = IIII_IIII;
		// 0000 1111 0000 1111 0000 1111 0000 1111
		buffer[8] = OOOO_IIII;
		buffer[9] = OOOO_IIII;
		buffer[10] = OOOO_IIII;
		buffer[11] = OOOO_IIII;
		// 1111 0000 1111 0000 1111 0000 1111 0000
		buffer[12] = IIII_OOOO;
		buffer[13] = IIII_OOOO;
		buffer[14] = IIII_OOOO;
		buffer[15] = IIII_OOOO;
		// 0101 0101 0101 0101 0101 0101 0101 0101
		buffer[16] = OIOI_OIOI;
		buffer[17] = OIOI_OIOI;
		buffer[18] = OIOI_OIOI;
		buffer[19] = OIOI_OIOI;
		// 1010 1010 1010 1010 1010 1010 1010 1010
		buffer[20] = IOIO_IOIO;
		buffer[21] = IOIO_IOIO;
		buffer[22] = IOIO_IOIO;
		buffer[23] = IOIO_IOIO;
		// 1000 0000 0000 0000 0000 0000 0000 0000
		buffer[24] = OOOO_OOOO;
		buffer[25] = OOOO_OOOO;
		buffer[26] = OOOO_OOOO;
		buffer[27] = IOOO_OOOO;
		// 0000 0000 0000 0000 0000 0000 0000 0001
		buffer[28] = OOOO_OOOI;
		buffer[29] = OOOO_OOOO;
		buffer[30] = OOOO_OOOO;
		buffer[31] = OOOO_OOOO;

		JStruct struct = new JStruct("8L");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0L, values[0]);
		assertEquals(4294967295L, values[1]);
		assertEquals(252645135L, values[2]);
		assertEquals(4042322160L, values[3]);
		assertEquals(1431655765L, values[4]);
		assertEquals(2863311530L, values[5]);
		assertEquals(2147483648L, values[6]);
		assertEquals(1L, values[7]);
	}

	@Test
	public void unpack_q_valuesTest() throws JStructException {
		byte[] buffer = new byte[64];
		// 0000 0000 0000 0000 0000 0000 0000 0000
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		buffer[2] = OOOO_OOOO;
		buffer[3] = OOOO_OOOO;
		buffer[4] = OOOO_OOOO;
		buffer[5] = OOOO_OOOO;
		buffer[6] = OOOO_OOOO;
		buffer[7] = OOOO_OOOO;
		// 1111 1111 1111 1111 1111 1111 1111 1111
		// 1111 1111 1111 1111 1111 1111 1111 1111
		buffer[8] = IIII_IIII;
		buffer[9] = IIII_IIII;
		buffer[10] = IIII_IIII;
		buffer[11] = IIII_IIII;
		buffer[12] = IIII_IIII;
		buffer[13] = IIII_IIII;
		buffer[14] = IIII_IIII;
		buffer[15] = IIII_IIII;
		// 0000 1111 0000 1111 0000 1111 0000 1111
		// 0000 1111 0000 1111 0000 1111 0000 1111
		buffer[16] = OOOO_IIII;
		buffer[17] = OOOO_IIII;
		buffer[18] = OOOO_IIII;
		buffer[19] = OOOO_IIII;
		buffer[20] = OOOO_IIII;
		buffer[21] = OOOO_IIII;
		buffer[22] = OOOO_IIII;
		buffer[23] = OOOO_IIII;
		// 1111 0000 1111 0000 1111 0000 1111 0000
		// 1111 0000 1111 0000 1111 0000 1111 0000
		buffer[24] = IIII_OOOO;
		buffer[25] = IIII_OOOO;
		buffer[26] = IIII_OOOO;
		buffer[27] = IIII_OOOO;
		buffer[28] = IIII_OOOO;
		buffer[29] = IIII_OOOO;
		buffer[30] = IIII_OOOO;
		buffer[31] = IIII_OOOO;
		// 0101 0101 0101 0101 0101 0101 0101 0101
		// 0101 0101 0101 0101 0101 0101 0101 0101
		buffer[32] = OIOI_OIOI;
		buffer[33] = OIOI_OIOI;
		buffer[34] = OIOI_OIOI;
		buffer[35] = OIOI_OIOI;
		buffer[36] = OIOI_OIOI;
		buffer[37] = OIOI_OIOI;
		buffer[38] = OIOI_OIOI;
		buffer[39] = OIOI_OIOI;
		// 1010 1010 1010 1010 1010 1010 1010 1010
		// 1010 1010 1010 1010 1010 1010 1010 1010
		buffer[40] = IOIO_IOIO;
		buffer[41] = IOIO_IOIO;
		buffer[42] = IOIO_IOIO;
		buffer[43] = IOIO_IOIO;
		buffer[44] = IOIO_IOIO;
		buffer[45] = IOIO_IOIO;
		buffer[46] = IOIO_IOIO;
		buffer[47] = IOIO_IOIO;
		// 1000 0000 0000 0000 0000 0000 0000 0000
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[48] = OOOO_OOOO;
		buffer[49] = OOOO_OOOO;
		buffer[50] = OOOO_OOOO;
		buffer[51] = OOOO_OOOO;
		buffer[52] = OOOO_OOOO;
		buffer[53] = OOOO_OOOO;
		buffer[54] = OOOO_OOOO;
		buffer[55] = IOOO_OOOO;
		// 0000 0000 0000 0000 0000 0000 0000 0000
		// 0000 0000 0000 0000 0000 0000 0000 0001
		buffer[56] = OOOO_OOOI;
		buffer[57] = OOOO_OOOO;
		buffer[58] = OOOO_OOOO;
		buffer[59] = OOOO_OOOO;
		buffer[60] = OOOO_OOOO;
		buffer[61] = OOOO_OOOO;
		buffer[62] = OOOO_OOOO;
		buffer[63] = OOOO_OOOO;

		JStruct struct = new JStruct("8q");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0L, values[0]);
		assertEquals(-1L, values[1]);
		assertEquals(1085102592571150095L, values[2]);
		assertEquals(-1085102592571150096L, values[3]);
		assertEquals(6148914691236517205L, values[4]);
		assertEquals(-6148914691236517206L, values[5]);
		assertEquals(-9223372036854775808L, values[6]);
		assertEquals(1L, values[7]);
	}

	@Test
	public void unpack_f_valuesTest() throws JStructException {
		byte[] buffer = new byte[32];
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		buffer[2] = OOOO_OOOO;
		buffer[3] = OOOO_OOOO;
		// 1111 1111 1111 1111 1111 1111 1111 1111
		buffer[4] = IIII_IIII;
		buffer[5] = IIII_IIII;
		buffer[6] = IIII_IIII;
		buffer[7] = IIII_IIII;
		// 0000 1111 0000 1111 0000 1111 0000 1111
		buffer[8] = OOOO_IIII;
		buffer[9] = OOOO_IIII;
		buffer[10] = OOOO_IIII;
		buffer[11] = OOOO_IIII;
		// 1111 0000 1111 0000 1111 0000 1111 0000
		buffer[12] = IIII_OOOO;
		buffer[13] = IIII_OOOO;
		buffer[14] = IIII_OOOO;
		buffer[15] = IIII_OOOO;
		// 0101 0101 0101 0101 0101 0101 0101 0101
		buffer[16] = OIOI_OIOI;
		buffer[17] = OIOI_OIOI;
		buffer[18] = OIOI_OIOI;
		buffer[19] = OIOI_OIOI;
		// 1010 1010 1010 1010 1010 1010 1010 1010
		buffer[20] = IOIO_IOIO;
		buffer[21] = IOIO_IOIO;
		buffer[22] = IOIO_IOIO;
		buffer[23] = IOIO_IOIO;
		// 1000 0000 0000 0000 0000 0000 0000 0000
		buffer[24] = OOOO_OOOO;
		buffer[25] = OOOO_OOOO;
		buffer[26] = OOOO_OOOO;
		buffer[27] = IOOO_OOOO;
		// 0000 0000 0000 0000 0000 0000 0000 0001
		buffer[28] = OOOO_OOOI;
		buffer[29] = OOOO_OOOO;
		buffer[30] = OOOO_OOOO;
		buffer[31] = OOOO_OOOO;

		JStruct struct = new JStruct("8f");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0.0f, values[0]);
		assertTrue(Float.isNaN((float) values[1]));
		assertEquals(7.0533445E-30f, values[2]);
		assertEquals(-5.9654142E29f, values[3]);
		assertEquals(1.46601547E13f, values[4]);
		assertEquals(-3.0316488E-13f, values[5]);
		assertEquals(-0.0f, values[6]);
		assertEquals(1.4E-45f, values[7]);
	}

	@Test
	public void unpack_d_valuesTest() throws JStructException {
		byte[] buffer = new byte[64];
		// 0000 0000 0000 0000 0000 0000 0000 0000
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[0] = OOOO_OOOO;
		buffer[1] = OOOO_OOOO;
		buffer[2] = OOOO_OOOO;
		buffer[3] = OOOO_OOOO;
		buffer[4] = OOOO_OOOO;
		buffer[5] = OOOO_OOOO;
		buffer[6] = OOOO_OOOO;
		buffer[7] = OOOO_OOOO;
		// 1111 1111 1111 1111 1111 1111 1111 1111
		// 1111 1111 1111 1111 1111 1111 1111 1111
		buffer[8] = IIII_IIII;
		buffer[9] = IIII_IIII;
		buffer[10] = IIII_IIII;
		buffer[11] = IIII_IIII;
		buffer[12] = IIII_IIII;
		buffer[13] = IIII_IIII;
		buffer[14] = IIII_IIII;
		buffer[15] = IIII_IIII;
		// 0000 1111 0000 1111 0000 1111 0000 1111
		// 0000 1111 0000 1111 0000 1111 0000 1111
		buffer[16] = OOOO_IIII;
		buffer[17] = OOOO_IIII;
		buffer[18] = OOOO_IIII;
		buffer[19] = OOOO_IIII;
		buffer[20] = OOOO_IIII;
		buffer[21] = OOOO_IIII;
		buffer[22] = OOOO_IIII;
		buffer[23] = OOOO_IIII;
		// 1111 0000 1111 0000 1111 0000 1111 0000
		// 1111 0000 1111 0000 1111 0000 1111 0000
		buffer[24] = IIII_OOOO;
		buffer[25] = IIII_OOOO;
		buffer[26] = IIII_OOOO;
		buffer[27] = IIII_OOOO;
		buffer[28] = IIII_OOOO;
		buffer[29] = IIII_OOOO;
		buffer[30] = IIII_OOOO;
		buffer[31] = IIII_OOOO;
		// 0101 0101 0101 0101 0101 0101 0101 0101
		// 0101 0101 0101 0101 0101 0101 0101 0101
		buffer[32] = OIOI_OIOI;
		buffer[33] = OIOI_OIOI;
		buffer[34] = OIOI_OIOI;
		buffer[35] = OIOI_OIOI;
		buffer[36] = OIOI_OIOI;
		buffer[37] = OIOI_OIOI;
		buffer[38] = OIOI_OIOI;
		buffer[39] = OIOI_OIOI;
		// 1010 1010 1010 1010 1010 1010 1010 1010
		// 1010 1010 1010 1010 1010 1010 1010 1010
		buffer[40] = IOIO_IOIO;
		buffer[41] = IOIO_IOIO;
		buffer[42] = IOIO_IOIO;
		buffer[43] = IOIO_IOIO;
		buffer[44] = IOIO_IOIO;
		buffer[45] = IOIO_IOIO;
		buffer[46] = IOIO_IOIO;
		buffer[47] = IOIO_IOIO;
		// 1000 0000 0000 0000 0000 0000 0000 0000
		// 0000 0000 0000 0000 0000 0000 0000 0000
		buffer[48] = OOOO_OOOO;
		buffer[49] = OOOO_OOOO;
		buffer[50] = OOOO_OOOO;
		buffer[51] = OOOO_OOOO;
		buffer[52] = OOOO_OOOO;
		buffer[53] = OOOO_OOOO;
		buffer[54] = OOOO_OOOO;
		buffer[55] = IOOO_OOOO;
		// 0000 0000 0000 0000 0000 0000 0000 0000
		// 0000 0000 0000 0000 0000 0000 0000 0001
		buffer[56] = OOOO_OOOI;
		buffer[57] = OOOO_OOOO;
		buffer[58] = OOOO_OOOO;
		buffer[59] = OOOO_OOOO;
		buffer[60] = OOOO_OOOO;
		buffer[61] = OOOO_OOOO;
		buffer[62] = OOOO_OOOO;
		buffer[63] = OOOO_OOOO;

		JStruct struct = new JStruct("8d");
		Object[] values = struct.unpack(buffer);
		assertEquals(8, values.length);

		assertEquals(0.0, values[0]);
		assertTrue(Double.isNaN((double) values[1]));
		assertEquals(3.81573682711801681951670695231E-236, values[2]);
		assertEquals(-1.07730874267432137203343331821E236, values[3]);
		assertEquals(1.19453052916149551265420436166E103, values[4]);
		assertEquals(-3.7206620809969885439391603375E-103, values[5]);
		assertEquals(-0.0, values[6]);
		assertEquals(4.94065645841246544176568792868E-324, values[7]);
	}

	@Test
	public void unpack_bool_valuesTest() throws JStructException {
		byte[] buffer = new byte[2];
		buffer[0] = OOOO_OOOO;
		buffer[1] = IIII_IIII;

		JStruct struct = new JStruct("2?");
		Object[] values = struct.unpack(buffer);

		assertFalse((boolean) values[0]);
		assertTrue((boolean) values[1]);
	}

}
