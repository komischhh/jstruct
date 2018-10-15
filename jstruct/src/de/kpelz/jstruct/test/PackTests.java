package de.kpelz.jstruct.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.kpelz.jstruct.main.JStruct;
import de.kpelz.jstruct.main.JStruct.JStructException;

public class PackTests {

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

	@Test
	public void pack_c_valuesTest() {

	}

	@Test
	public void pack_b_valuesTest() throws JStructException {
		Integer[] values = new Integer[6];
		values[0] = 0;
		values[1] = -1;
		values[2] = 15;
		values[3] = -16;
		values[4] = -128;
		values[5] = 127;

		JStruct struct = new JStruct("6b");
		byte[] bytes = struct.pack(values);
		assertEquals(6, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(IIII_IIII, bytes[1]);
		assertEquals(OOOO_IIII, bytes[2]);
		assertEquals(IIII_OOOO, bytes[3]);
		assertEquals(IOOO_OOOO, bytes[4]);
		assertEquals(OIII_IIII, bytes[5]);
	}

	@Test
	public void pack_B_valuesTest() throws JStructException {
		Integer[] values = new Integer[6];
		values[0] = 0;
		values[1] = 255;
		values[2] = 15;
		values[3] = 240;
		values[4] = 128;
		values[5] = 127;

		JStruct struct = new JStruct("6B");
		byte[] bytes = struct.pack(values);
		assertEquals(6, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(IIII_IIII, bytes[1]);
		assertEquals(OOOO_IIII, bytes[2]);
		assertEquals(IIII_OOOO, bytes[3]);
		assertEquals(IOOO_OOOO, bytes[4]);
		assertEquals(OIII_IIII, bytes[5]);
	}

	@Test
	public void pack_bool_valuesTest() {

	}

	@Test
	public void pack_h_valuesTest() throws JStructException {
		Integer[] values = new Integer[8];
		values[0] = 0;
		values[1] = -1;
		values[2] = 3855;
		values[3] = -3856;
		values[4] = -21846;
		values[5] = 21845;
		values[6] = -32768;
		values[7] = 1;

		JStruct struct = new JStruct("8h");
		byte[] bytes = struct.pack(values);
		assertEquals(16, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(OOOO_OOOO, bytes[1]);
		assertEquals(IIII_IIII, bytes[2]);
		assertEquals(IIII_IIII, bytes[3]);
		assertEquals(OOOO_IIII, bytes[4]);
		assertEquals(OOOO_IIII, bytes[5]);
		assertEquals(IIII_OOOO, bytes[6]);
		assertEquals(IIII_OOOO, bytes[7]);
		assertEquals(IOIO_IOIO, bytes[8]);
		assertEquals(IOIO_IOIO, bytes[9]);
		assertEquals(OIOI_OIOI, bytes[10]);
		assertEquals(OIOI_OIOI, bytes[11]);
		assertEquals(OOOO_OOOO, bytes[12]);
		assertEquals(IOOO_OOOO, bytes[13]);
		assertEquals(OOOO_OOOI, bytes[14]);
		assertEquals(OOOO_OOOO, bytes[15]);
	}

	@Test
	public void pack_H_valuesTest() throws JStructException {
		Integer[] values = new Integer[8];
		values[0] = 0;
		values[1] = 65535;
		values[2] = 3855;
		values[3] = 61680;
		values[4] = 43690;
		values[5] = 21845;
		values[6] = 32768;
		values[7] = 1;

		JStruct struct = new JStruct("8h");
		byte[] bytes = struct.pack(values);
		assertEquals(16, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(OOOO_OOOO, bytes[1]);
		assertEquals(IIII_IIII, bytes[2]);
		assertEquals(IIII_IIII, bytes[3]);
		assertEquals(OOOO_IIII, bytes[4]);
		assertEquals(OOOO_IIII, bytes[5]);
		assertEquals(IIII_OOOO, bytes[6]);
		assertEquals(IIII_OOOO, bytes[7]);
		assertEquals(IOIO_IOIO, bytes[8]);
		assertEquals(IOIO_IOIO, bytes[9]);
		assertEquals(OIOI_OIOI, bytes[10]);
		assertEquals(OIOI_OIOI, bytes[11]);
		assertEquals(OOOO_OOOO, bytes[12]);
		assertEquals(IOOO_OOOO, bytes[13]);
		assertEquals(OOOO_OOOI, bytes[14]);
		assertEquals(OOOO_OOOO, bytes[15]);
	}

	@Test
	public void pack_i_valuesTest() throws JStructException {
		Integer[] values = new Integer[8];
		values[0] = 0;
		values[1] = -1;
		values[2] = 252645135;
		values[3] = -252645136;
		values[4] = 1431655765;
		values[5] = -1431655766;
		values[6] = -2147483648;
		values[7] = 1;

		JStruct struct = new JStruct("8i");
		byte[] bytes = struct.pack(values);
		assertEquals(8 * 4, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(OOOO_OOOO, bytes[1]);
		assertEquals(OOOO_OOOO, bytes[2]);
		assertEquals(OOOO_OOOO, bytes[3]);
		assertEquals(IIII_IIII, bytes[4]);
		assertEquals(IIII_IIII, bytes[5]);
		assertEquals(IIII_IIII, bytes[6]);
		assertEquals(IIII_IIII, bytes[7]);
		assertEquals(OOOO_IIII, bytes[8]);
		assertEquals(OOOO_IIII, bytes[9]);
		assertEquals(OOOO_IIII, bytes[10]);
		assertEquals(OOOO_IIII, bytes[11]);
		assertEquals(IIII_OOOO, bytes[12]);
		assertEquals(IIII_OOOO, bytes[13]);
		assertEquals(IIII_OOOO, bytes[14]);
		assertEquals(IIII_OOOO, bytes[15]);
		assertEquals(OIOI_OIOI, bytes[16]);
		assertEquals(OIOI_OIOI, bytes[17]);
		assertEquals(OIOI_OIOI, bytes[18]);
		assertEquals(OIOI_OIOI, bytes[19]);
		assertEquals(IOIO_IOIO, bytes[20]);
		assertEquals(IOIO_IOIO, bytes[21]);
		assertEquals(IOIO_IOIO, bytes[22]);
		assertEquals(IOIO_IOIO, bytes[23]);
		assertEquals(OOOO_OOOO, bytes[24]);
		assertEquals(OOOO_OOOO, bytes[25]);
		assertEquals(OOOO_OOOO, bytes[26]);
		assertEquals(IOOO_OOOO, bytes[27]);
		assertEquals(OOOO_OOOI, bytes[28]);
		assertEquals(OOOO_OOOO, bytes[29]);
		assertEquals(OOOO_OOOO, bytes[30]);
		assertEquals(OOOO_OOOO, bytes[31]);
	}

	@Test
	public void pack_I_valuesTest() throws JStructException {
		Long[] values = new Long[8];
		values[0] = 0L;
		values[1] = 4294967295L;
		values[2] = 252645135L;
		values[3] = 4042322160L;
		values[4] = 1431655765L;
		values[5] = 2863311530L;
		values[6] = 2147483648L;
		values[7] = 1L;

		JStruct struct = new JStruct("8I");
		byte[] bytes = struct.pack(values);
		assertEquals(8 * 4, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(OOOO_OOOO, bytes[1]);
		assertEquals(OOOO_OOOO, bytes[2]);
		assertEquals(OOOO_OOOO, bytes[3]);
		assertEquals(IIII_IIII, bytes[4]);
		assertEquals(IIII_IIII, bytes[5]);
		assertEquals(IIII_IIII, bytes[6]);
		assertEquals(IIII_IIII, bytes[7]);
		assertEquals(OOOO_IIII, bytes[8]);
		assertEquals(OOOO_IIII, bytes[9]);
		assertEquals(OOOO_IIII, bytes[10]);
		assertEquals(OOOO_IIII, bytes[11]);
		assertEquals(IIII_OOOO, bytes[12]);
		assertEquals(IIII_OOOO, bytes[13]);
		assertEquals(IIII_OOOO, bytes[14]);
		assertEquals(IIII_OOOO, bytes[15]);
		assertEquals(OIOI_OIOI, bytes[16]);
		assertEquals(OIOI_OIOI, bytes[17]);
		assertEquals(OIOI_OIOI, bytes[18]);
		assertEquals(OIOI_OIOI, bytes[19]);
		assertEquals(IOIO_IOIO, bytes[20]);
		assertEquals(IOIO_IOIO, bytes[21]);
		assertEquals(IOIO_IOIO, bytes[22]);
		assertEquals(IOIO_IOIO, bytes[23]);
		assertEquals(OOOO_OOOO, bytes[24]);
		assertEquals(OOOO_OOOO, bytes[25]);
		assertEquals(OOOO_OOOO, bytes[26]);
		assertEquals(IOOO_OOOO, bytes[27]);
		assertEquals(OOOO_OOOI, bytes[28]);
		assertEquals(OOOO_OOOO, bytes[29]);
		assertEquals(OOOO_OOOO, bytes[30]);
		assertEquals(OOOO_OOOO, bytes[31]);
	}

	@Test
	public void pack_l_valuesTest() throws JStructException {
		Integer[] values = new Integer[8];
		values[0] = 0;
		values[1] = -1;
		values[2] = 252645135;
		values[3] = -252645136;
		values[4] = 1431655765;
		values[5] = -1431655766;
		values[6] = -2147483648;
		values[7] = 1;

		JStruct struct = new JStruct("8l");
		byte[] bytes = struct.pack(values);
		assertEquals(8 * 4, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(OOOO_OOOO, bytes[1]);
		assertEquals(OOOO_OOOO, bytes[2]);
		assertEquals(OOOO_OOOO, bytes[3]);
		assertEquals(IIII_IIII, bytes[4]);
		assertEquals(IIII_IIII, bytes[5]);
		assertEquals(IIII_IIII, bytes[6]);
		assertEquals(IIII_IIII, bytes[7]);
		assertEquals(OOOO_IIII, bytes[8]);
		assertEquals(OOOO_IIII, bytes[9]);
		assertEquals(OOOO_IIII, bytes[10]);
		assertEquals(OOOO_IIII, bytes[11]);
		assertEquals(IIII_OOOO, bytes[12]);
		assertEquals(IIII_OOOO, bytes[13]);
		assertEquals(IIII_OOOO, bytes[14]);
		assertEquals(IIII_OOOO, bytes[15]);
		assertEquals(OIOI_OIOI, bytes[16]);
		assertEquals(OIOI_OIOI, bytes[17]);
		assertEquals(OIOI_OIOI, bytes[18]);
		assertEquals(OIOI_OIOI, bytes[19]);
		assertEquals(IOIO_IOIO, bytes[20]);
		assertEquals(IOIO_IOIO, bytes[21]);
		assertEquals(IOIO_IOIO, bytes[22]);
		assertEquals(IOIO_IOIO, bytes[23]);
		assertEquals(OOOO_OOOO, bytes[24]);
		assertEquals(OOOO_OOOO, bytes[25]);
		assertEquals(OOOO_OOOO, bytes[26]);
		assertEquals(IOOO_OOOO, bytes[27]);
		assertEquals(OOOO_OOOI, bytes[28]);
		assertEquals(OOOO_OOOO, bytes[29]);
		assertEquals(OOOO_OOOO, bytes[30]);
		assertEquals(OOOO_OOOO, bytes[31]);
	}

	@Test
	public void pack_L_valuesTest() throws JStructException {
		Long[] values = new Long[8];
		values[0] = 0L;
		values[1] = 4294967295L;
		values[2] = 252645135L;
		values[3] = 4042322160L;
		values[4] = 1431655765L;
		values[5] = 2863311530L;
		values[6] = 2147483648L;
		values[7] = 1L;

		JStruct struct = new JStruct("8L");
		byte[] bytes = struct.pack(values);
		assertEquals(8 * 4, bytes.length);

		assertEquals(OOOO_OOOO, bytes[0]);
		assertEquals(OOOO_OOOO, bytes[1]);
		assertEquals(OOOO_OOOO, bytes[2]);
		assertEquals(OOOO_OOOO, bytes[3]);
		assertEquals(IIII_IIII, bytes[4]);
		assertEquals(IIII_IIII, bytes[5]);
		assertEquals(IIII_IIII, bytes[6]);
		assertEquals(IIII_IIII, bytes[7]);
		assertEquals(OOOO_IIII, bytes[8]);
		assertEquals(OOOO_IIII, bytes[9]);
		assertEquals(OOOO_IIII, bytes[10]);
		assertEquals(OOOO_IIII, bytes[11]);
		assertEquals(IIII_OOOO, bytes[12]);
		assertEquals(IIII_OOOO, bytes[13]);
		assertEquals(IIII_OOOO, bytes[14]);
		assertEquals(IIII_OOOO, bytes[15]);
		assertEquals(OIOI_OIOI, bytes[16]);
		assertEquals(OIOI_OIOI, bytes[17]);
		assertEquals(OIOI_OIOI, bytes[18]);
		assertEquals(OIOI_OIOI, bytes[19]);
		assertEquals(IOIO_IOIO, bytes[20]);
		assertEquals(IOIO_IOIO, bytes[21]);
		assertEquals(IOIO_IOIO, bytes[22]);
		assertEquals(IOIO_IOIO, bytes[23]);
		assertEquals(OOOO_OOOO, bytes[24]);
		assertEquals(OOOO_OOOO, bytes[25]);
		assertEquals(OOOO_OOOO, bytes[26]);
		assertEquals(IOOO_OOOO, bytes[27]);
		assertEquals(OOOO_OOOI, bytes[28]);
		assertEquals(OOOO_OOOO, bytes[29]);
		assertEquals(OOOO_OOOO, bytes[30]);
		assertEquals(OOOO_OOOO, bytes[31]);
	}

	@Test
	public void pack_q_valuesTest() throws JStructException {
		Long[] values = new Long[8];
		values[0] = 0L;
		values[1] = -1L;
		values[2] = 1085102592571150095L;
		values[3] = -1085102592571150096L;
		values[4] = 6148914691236517205L;
		values[5] = -6148914691236517206L;
		values[6] = -9223372036854775808L;
		values[7] = 1L;

		JStruct struct = new JStruct("8q");
		byte[] bytes = struct.pack(values);
		assertEquals(8 * 8, bytes.length);
	}

	@Test
	public void pack_f_valuesTest() {

	}

	@Test
	public void pack_d_valuesTest() {

	}

	@Test
	public void pack_s_valuesTest() {

	}

}
