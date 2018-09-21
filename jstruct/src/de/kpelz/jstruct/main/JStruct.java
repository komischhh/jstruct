package de.kpelz.jstruct.main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * Equivalence to <code>struct</code> in Python. This plugin converts C structs
 * into Java values and vica versa.<br>
 * <br>
 * <b>Byte Order, Size &amp; Alignment</b><br>
 * <table border="1px solid black" summary ="">
 * <tr>
 * <th scope="col">Character</th>
 * <th scope="col">Byte order</th>
 * <th scope="col">Size</th>
 * <th scope="col">Alignment</th>
 * </tr>
 * <tr>
 * <td><code>@</code></td>
 * <td>native</td>
 * <td>native</td>
 * <td>native</td>
 * </tr>
 * <tr>
 * <td><code>=</code></td>
 * <td>native</td>
 * <td>standard</td>
 * <td>none</td>
 * </tr>
 * <tr>
 * <td><code>&lt;</code></td>
 * <td>little-endian</td>
 * <td>standard</td>
 * <td>none</td>
 * </tr>
 * <tr>
 * <td><code>&gt;</code></td>
 * <td>big-endian</td>
 * <td>standard</td>
 * <td>none</td>
 * </tr>
 * <tr>
 * <td><code>!</code></td>
 * <td>network (=big-endian)</td>
 * <td>standard</td>
 * <td>none</td>
 * </tr>
 * </table>
 * '<code>@</code>' is currently not supported. Please use '<code>&lt;</code>'
 * or '<code>&gt;</code>'.<br>
 * <br>
 * <b>Format Characters</b><br>
 * <table border="1px solid black" summary="">
 * <tr>
 * <th scope="col">Format</th>
 * <th scope="col">C Type</th>
 * <th scope="col">Java Type</th>
 * <th scope="col">Standard size</th>
 * </tr>
 * <tr>
 * <td><code>x</code></td>
 * <td>pad byte</td>
 * </tr>
 * <tr>
 * <td><code>c</code></td>
 * <td>char</td>
 * <td>char</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td><code>b</code></td>
 * <td>signed char</td>
 * <td>int</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td><code>B</code></td>
 * <td>unsigned char</td>
 * <td>int</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td><code>?</code></td>
 * <td>_Bool</td>
 * <td>boolean</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td><code>h</code></td>
 * <td>short</td>
 * <td>int</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td><code>H</code></td>
 * <td>unsigned short</td>
 * <td>int</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td><code>i</code></td>
 * <td>int</td>
 * <td>int</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td><code>I</code></td>
 * <td>unsigned int</td>
 * <td>int</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td><code>l</code></td>
 * <td>long</td>
 * <td>int</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td><code>L</code></td>
 * <td>unsigned long</td>
 * <td>long</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td><code>q</code></td>
 * <td>long long</td>
 * <td>long</td>
 * <td>8</td>
 * </tr>
 * <tr>
 * <td><code>Q</code></td>
 * <td>unsigned long long</td>
 * <td>no support</td>
 * <td>8</td>
 * </tr>
 * <tr>
 * <td><code>n</code></td>
 * <td>ssize_t</td>
 * <td>no support</td>
 * </tr>
 * <tr>
 * <td><code>N</code></td>
 * <td>size_t</td>
 * <td>no support</td>
 * </tr>
 * <tr>
 * <td><code>e</code></td>
 * <td>half-precision float</td>
 * <td>no support</td>
 * </tr>
 * <tr>
 * <td><code>f</code></td>
 * <td>float</td>
 * <td>float</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td><code>d</code></td>
 * <td>double</td>
 * <td>double</td>
 * <td>8</td>
 * </tr>
 * <tr>
 * <td><code>s</code></td>
 * <td>char[]</td>
 * <td>String</td>
 * </tr>
 * <tr>
 * <td><code>p</code></td>
 * <td>char[]</td>
 * <td>String</td>
 * </tr>
 * <tr>
 * <td><code>P</code></td>
 * <td>void *</td>
 * <td>no support</td>
 * </tr>
 * </table>
 */
public class JStruct {

	private final static Map<Character, Integer> FORMAT_CHARACTERS_SIZES;

	static {
		Map<Character, Integer> map = new HashMap<>();
		map.put('x', 1);
		map.put('c', 1);
		map.put('b', 1);
		map.put('B', 1);
		map.put('?', 1);
		map.put('h', 2);
		map.put('H', 2);
		map.put('i', 4);
		map.put('I', 4);
		map.put('l', 4);
		map.put('L', 4);
		map.put('q', 8);
		map.put('f', 4);
		map.put('d', 8);
		map.put('s', 1);
		map.put('p', 1);
		FORMAT_CHARACTERS_SIZES = map;
	}

	private final static Map<Character, ByteOrder> BYTE_ORDER_IDENTIFIER;

	static {
		Map<Character, ByteOrder> map = new HashMap<>();
		map.put('=', ByteOrder.nativeOrder());
		map.put('<', ByteOrder.LITTLE_ENDIAN);
		map.put('>', ByteOrder.BIG_ENDIAN);
		map.put('!', ByteOrder.nativeOrder());
		BYTE_ORDER_IDENTIFIER = map;
	}

	private String format;

	private char[] formatArray;

	private int size;

	private ByteOrder byteOrder;

	/**
	 * Constructor for {@link JStruct}
	 * 
	 * @param format
	 *           of the JStruct
	 * @throws JStructException
	 *            thrown if format is invalid or format starts with an
	 *            '<code>@</code>'
	 */
	public JStruct(String format) throws JStructException {
		if (!verifyFormat(format)) {
			throw new JStructException("Invalid struct format.");
		}
		this.format = format;
		this.formatArray = convertFormatToArray(format);
		this.size = calculateSize(format);
		if (!format.matches("^[=<>!@]{1}")) {
			Logger.getAnonymousLogger().log(Level.WARNING,
					"The format does not start have an identifier for byte order, size and alignment!"
							+ " Please prefer to use '<' or '>',"
							+ " else standard size and native byte order will be used");
		} else if (format.startsWith("@")) {
			throw new JStructException(
					"Structs starting with '@' are currently not supported. Please prefer to use '<' or '>'.");
		} else if (format.matches("^[=<>!]{1}")) {
			byteOrder = BYTE_ORDER_IDENTIFIER.get(formatArray[0]);
		} else {
			byteOrder = ByteOrder.nativeOrder();
		}
	}

	/**
	 * Packs <code>values</code> into a byte array. The type of each value have
	 * to match the type of the belonging format character. For matching types
	 * see the table 'Format Characters' in {@link JStruct}.
	 * 
	 * @param values
	 *           to pack
	 * @return byte array with the values packed as bytes
	 * @throws JStructException
	 */
	public byte[] pack(Object... values) throws JStructException {
		if (calculateSizeOfStringArray(formatArray) != values.length) {
			throw new JStructException(
					"The number of values does not match the number of values from the format.");
		}
		byte[] result = new byte[size];
		int resPos = 0;
		int valPos = 0;

		for (int j = 0; j < formatArray.length; j++) {
			char type = formatArray[j];
			ByteBuffer bb = ByteBuffer.allocate(FORMAT_CHARACTERS_SIZES.get(type));
			bb.order(byteOrder);

			if (type == 'x') {
				result[resPos++] = 0;
			} else if (type == 'c') {
				if (!values[valPos].getClass().equals(char.class)) {
					throw new JStructException("Excpected a char but got a "
							+ values[valPos].getClass().getName() + ".");
				}
				char c = (char) values[valPos++];
				bb.putChar(c);
				result[resPos++] = bb.get();
			} else if (type == '?') {
				if (!values[valPos].getClass().equals(boolean.class)) {
					throw new JStructException("Excpected a boolean but got a "
							+ values[valPos].getClass().getName() + ".");
				}
				boolean b = (boolean) values[valPos++];
				result[resPos++] = (byte) (b ? 1 : 0);
			} else if (type == 'b' || type == 'B' || type == 'h' || type == 'H'
					|| type == 'i' || type == 'I' || type == 'l') {
				if (!values[valPos].getClass().equals(int.class)) {
					throw new JStructException("Excpected a int but got a "
							+ values[valPos].getClass().getName() + ".");
				}
				int i = (int) values[valPos++];
				bb.putInt(i);
				bb.get(result, resPos, bb.capacity());
				resPos += bb.capacity();
			} else if (type == 'L' || type == 'q') {
				if (!values[valPos].getClass().equals(long.class)) {
					throw new JStructException("Excpected a long but got a "
							+ values[valPos].getClass().getName() + ".");
				}
				long l = (long) values[valPos++];
				bb.putLong(l);
				bb.get(result, resPos, bb.capacity());
				resPos += bb.capacity();
			} else if (type == 'f') {
				if (!values[valPos].getClass().equals(float.class)) {
					throw new JStructException("Excpected a float but got a "
							+ values[valPos].getClass().getName() + ".");
				}
				float f = (float) values[valPos];
				bb.putFloat(f);
				bb.get(result, resPos, bb.capacity());
				resPos += bb.capacity();
			} else if (type == 'd') {
				if (!values[valPos].getClass().equals(double.class)) {
					throw new JStructException("Excpected a double but got a "
							+ values[valPos].getClass().getName() + ".");
				}
				double d = (double) values[valPos++];
				bb.putDouble(d);
				bb.get(result, resPos, bb.capacity());
				resPos += bb.capacity();
			} else if (type == 's') {
				if (j > 0 && formatArray[j - 1] == 's') {
					continue;
				} else {
					if (!values[valPos].getClass().equals(String.class)) {
						throw new JStructException("Excpected a String but got a "
								+ values[valPos].getClass().getName() + ".");
					}
					String s = (String) values[valPos++];
					char[] cs = s.toCharArray();
					for (char c : cs) {
						bb.putChar(c);
						result[resPos++] = bb.get();
						bb.clear();
					}
				}
			}
		}
		return result;
	}

	/**
	 * Packs <code>values</code> into <code>buffer</code> starting at
	 * <code>offset</code>.
	 * 
	 * @param buffer
	 *           to pack into the values as bytes
	 * @param offset
	 *           should not be <code>null</code>
	 * @param values
	 *           to pack
	 * @throws JStructException
	 */
	public void packInto(byte[] buffer, int offset, Object... values)
			throws JStructException {
		if ((buffer.length - offset) < size) {
			throw new JStructException(
					"buffer have not enough space for the values. " + size
							+ " bytes required, " + (buffer.length - offset)
							+ " bytes availaible.");
		}
		byte[] result = this.pack(values);
		for (int i = offset; i < buffer.length; i++) {
			buffer[i] = result[i - offset];
		}
	}

	/**
	 * Packs <code>values</code> into <code>buffer</code>.
	 * 
	 * @param buffer
	 *           to pack into the values as bytes
	 * @param values
	 *           to pack
	 * @throws JStructException
	 */
	public void packInto(byte[] buffer, Object... values)
			throws JStructException {
		if (buffer.length < size) {
			throw new JStructException(
					"buffer have not enough space for the values. " + size
							+ " bytes required, " + buffer.length
							+ " bytes availaible.");
		}
		byte[] result = this.pack(values);
		for (int i = 0; i < result.length; i++) {
			buffer[i] = result[i];
		}
	}

	/**
	 * Unpacks the <code>buffer</code> specified by the <code>format</code>.
	 * 
	 * @param buffer
	 *           to unpack
	 * @return An Object array containing the values
	 * @throws JStructException
	 */
	public Object[] unpack(byte[] buffer) throws JStructException {
		if (buffer.length != calculateSize(format)) {
			throw new JStructException("The buffers size is wrong. It should be "
					+ calculateSize(format) + " but it's " + buffer.length + ".");
		}
		Object[] result = new Object[calculateSizeOfStringArray(formatArray)];
		int resPos = 0;
		int pos = 0;
		StringBuilder sb = new StringBuilder();

		for (int j = 0; j < formatArray.length; j++) {
			char type = formatArray[j];
			if (!FORMAT_CHARACTERS_SIZES.keySet().contains(type)) {
				continue;
			}
			if (type == 'x') {
				pos++;
			}
			ByteBuffer bb = ByteBuffer.allocate(FORMAT_CHARACTERS_SIZES.get(type));
			bb.order(byteOrder);
			for (int x = 0; x < FORMAT_CHARACTERS_SIZES.get(type); x++) {
				bb.put(buffer[pos++]);
			}
			bb.flip();
			if (type == 'c') {
				char c = (char) bb.get();
				result[resPos++] = c;
			} else if (type == 'b') {
				int i = bb.get();
				result[resPos++] = i;
			} else if (type == 'B') {
				int i = (bb.get() & 0xff);
				result[resPos++] = i;
			} else if (type == 'h') {
				int i = bb.getShort();
				result[resPos++] = i;
			} else if (type == 'H') {
				int i = ((bb.get() & 0xff) | (short) (bb.get() << 8));
				result[resPos++] = i;
			} else if (type == 'i' || type == 'l') {
				int i = bb.getInt();
				result[resPos++] = i;
			} else if (type == 'I') {
				int i = ((bb.get() & 0xff) | ((short) (bb.get() << 8))
						| ((int) (bb.get() << 16)));
				result[resPos++] = i;
			} else if (type == '?') {
				boolean b = !(bb.get() == 0);
				result[resPos++] = b;
			} else if (type == 'L') {
				long l = ((bb.get() & 0xff) | ((short) (bb.get() << 8))
						| ((int) (bb.get() << 16)) | ((long) (bb.get() << 24)));
				result[resPos++] = l;
			} else if (type == 'q') {
				long l = bb.getLong();
				result[resPos++] = l;
			} else if (type == 'f') {
				float f = bb.getFloat();
				result[resPos++] = f;
			} else if (type == 'd') {
				double d = bb.getDouble();
				result[resPos++] = d;
			} else if (type == 's') {
				char c = bb.getChar();
				sb.append(c);
				if (formatArray.length == j || !(formatArray[j + 1] == 's')) {
					result[resPos++] = sb.toString();
					sb = new StringBuilder();
				}
			}
		}
		return result;
	}

	/**
	 * Unpacks <code>buffer</code> specified by the <code>format</code> starting
	 * from <code>offset</code>.
	 * 
	 * @param buffer
	 *           to unpack
	 * @param offset
	 *           where unpacking of buffer starts
	 * @return An Object array containing the values
	 * @throws JStructException
	 */
	public Object[] unpackFrom(byte[] buffer, int offset)
			throws JStructException {
		return this.unpackFrom(buffer, offset);
	}

	public Object[] iterUnpack(byte[] buffer) {
		return new Object[0];
	}

	/**
	 * Packs <code>values</code> into a byte array. The type of each value have
	 * to match the type of the belonging format character. For matching types
	 * see the table 'Format Characters' in {@link JStruct}.
	 * 
	 * @param format
	 *           specifying byte array structure
	 * @param values
	 *           to pack
	 * @return byte array with the values packed as bytes
	 * @throws JStructException
	 */
	public static byte[] pack(String format, Object... values)
			throws JStructException {
		JStruct jStruct = new JStruct(format);
		return jStruct.pack(values);
	}

	/**
	 * Packs <code>values</code> into <code>buffer</code> starting at
	 * <code>offset</code>.
	 * 
	 * @param format
	 *           specifying byte array structure
	 * @param buffer
	 *           to pack into the values as bytes
	 * @param offset
	 *           should not be <code>null</code>
	 * @param values
	 *           to pack
	 * @throws JStructException
	 */
	public static void packInto(String format, byte[] buffer, int offset,
			Object... values) throws JStructException {
		JStruct jStruct = new JStruct(format);
		if ((buffer.length - offset) < jStruct.size) {
			throw new JStructException(
					"buffer have not enough space for the values. " + jStruct.size
							+ " bytes required, " + (buffer.length - offset)
							+ " bytes availaible.");
		}
		byte[] result = jStruct.pack(values);
		for (int i = offset; i < buffer.length; i++) {
			buffer[i] = result[i - offset];
		}
	}

	/**
	 * Packs <code>values</code> into <code>buffer</code>.
	 * 
	 * @param format
	 *           specifying byte array structure
	 * @param buffer
	 *           to pack into the values as bytes
	 * @param values
	 *           to pack
	 * @throws JStructException
	 */
	public static void packInto(String format, byte[] buffer, Object... values)
			throws JStructException {
		JStruct jStruct = new JStruct(format);
		if (buffer.length < jStruct.size) {
			throw new JStructException(
					"buffer have not enough space for the values. " + jStruct.size
							+ " bytes required, " + buffer.length
							+ " bytes availaible.");
		}
		byte[] result = jStruct.pack(values);
		for (int i = 0; i < result.length; i++) {
			buffer[i] = result[i];
		}
	}

	/**
	 * Unpacks the <code>buffer</code> specified by the <code>format</code>.
	 * 
	 * @param format
	 *           specifying byte array structure
	 * @param buffer
	 *           to unpack
	 * @return An Object array containing the values
	 * @throws JStructException
	 */
	public static Object[] unpack(String format, byte[] buffer)
			throws JStructException {
		JStruct jStruct = new JStruct(format);
		return jStruct.unpack(buffer);
	}

	/**
	 * Unpacks <code>buffer</code> specified by the <code>format</code> starting
	 * from <code>offset</code>.
	 * 
	 * @param format
	 *           specifying byte array structure
	 * @param buffer
	 *           to unpack
	 * @param offset
	 *           where unpacking of buffer starts
	 * @return An Object array containing the values
	 * @throws JStructException
	 */
	public static Object[] unpackFrom(String format, byte[] buffer, int offset)
			throws JStructException {
		JStruct jStruct = new JStruct(format);
		return jStruct.unpackFrom(buffer, offset);
	}

	public static Object[] iterUnpack(String format, byte[] buffer) {
		return new String[0];
	}

	/**
	 * Calculates the size of the byte array for the <code>format</code>.
	 * 
	 * @param format
	 *           to calculate size
	 * @return size of byte array
	 */
	public static int calculateSize(String format) {
		int size = 0;
		int count = 1;
		StringBuilder countReader = new StringBuilder();
		for (char c : format.toCharArray()) {
			if (Character.isDigit(c)) {
				countReader.append(c);
			} else if (FORMAT_CHARACTERS_SIZES.keySet().contains(c)) {
				if (countReader.length() != 0) {
					count = Integer.parseInt(countReader.toString());
					countReader = new StringBuilder();
				}
				size += FORMAT_CHARACTERS_SIZES.get(c) * count;
				count = 1;
			}
		}
		return size;
	}

	private static int calculateSizeOfStringArray(char[] formatArray) {
		int result = 0;
		for (char c : formatArray) {
			if (FORMAT_CHARACTERS_SIZES.keySet().contains(c) && c != 'x') {
				result++;
			}
		}
		return result;
	}

	private static char[] convertFormatToArray(String format) {
		char[] result = new char[calculateSize(format)];
		int pos = 0;
		int count = 1;
		StringBuilder sb = new StringBuilder();
		for (char c : format.toCharArray()) {
			if (Character.isDigit(c)) {
				sb.append(c);
			} else if (FORMAT_CHARACTERS_SIZES.keySet().contains(c)) {
				if (sb.length() != 0) {
					count = Integer.parseInt(sb.toString());
					sb = new StringBuilder();
				}
				for (int i = 0; i < count; i++) {
					result[pos++] = c;
				}
				count = 1;
			}
		}
		return result;
	}

	private static boolean verifyFormat(String format) {
		StringBuilder sb = new StringBuilder();
		sb.append("^[@=<>!]?[0-9");
		for (char c : FORMAT_CHARACTERS_SIZES.keySet()) {
			sb.append(c);
		}
		sb.append("]+$");
		return format.matches(sb.toString());
	}

	/**
	 * @author Konstantin Pelz
	 */
	@SuppressWarnings("serial")
	public static class JStructException extends Exception {

		public JStructException(String message) {
			super(message);
		}
	}
}
