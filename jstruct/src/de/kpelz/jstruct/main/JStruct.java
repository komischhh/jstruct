package de.kpelz.jstruct.main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class JStruct {

	private static Map<Character, Integer> formatCharacterSizes;

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
		/*
		 * currently not supported TODO use BIG Integer map.put('Q', 8);
		 */
		/*
		 * currently no support map.put('n', -1); map.put('N', -1); map.put('e',
		 * 2);
		 */
		map.put('f', 4);
		map.put('d', 8);
		map.put('s', 1);
		map.put('p', 1);
		/*
		 * currently no support map.put('P', -1);
		 */
		formatCharacterSizes = map;
	}

	private String format;

	private char[] formatArray;

	private int size;

	private ByteOrder byteOrder;

	public JStruct(String format) throws JStructException {
		if (!verifyFormat(format)) {
			throw new JStructException("Invalid struct format.");
		}
		this.format = format;
		this.formatArray = convertFormatToArray(format);
		this.size = calculateSize(format);
		if(! format.matches("^[=<>!]{1}")) {
			// TODO Warn Log
		}
		if (format.startsWith("<")) {
			byteOrder = ByteOrder.LITTLE_ENDIAN;
		} else if (format.startsWith(">")) {
			byteOrder = ByteOrder.BIG_ENDIAN;
		} else if (format.startsWith("@")) {
			throw new JStructException(
					"Structs starting with '@' are currently not supported. Please use '<' or '>'.");
		} else {
			byteOrder = ByteOrder.nativeOrder();
		}
	}

	public byte[] pack(Object... values) {
		return pack(format, values);
	}

	public byte[] packInto(byte[] buffer, int offset, Object... values) {
		return packInto(format, buffer, offset, values);
	}

	public Object[] unpack(byte[] buffer) throws JStructException {
		return unpack(format, buffer);
	}

	public Object[] unpackFrom(byte[] buffer, int offset)
			throws JStructException {
		return unpackFrom(format, buffer, offset);
	}

	public Object[] unpackFrom(byte[] buffer) throws JStructException {
		return unpackFrom(buffer, 0);
	}

	public Object[] iterUnpack(byte[] buffer) {
		return iterUnpack(format, buffer);
	}

	public static byte[] pack(String format, Object... values) {
		return new byte[0];
	}

	public static byte[] packInto(String format, byte[] buffer, int offset,
			Object... values) {
		return new byte[0];
	}

	public static Object[] unpack(String format, byte[] buffer)
			throws JStructException {
		if (buffer.length != calculateSize(format)) {
			throw new JStructException("The buffers size is wrong. It should be "
					+ calculateSize(format) + " but it's " + buffer.length + ".");
		}
		JStruct jStruct = new JStruct(format);
		char[] formatArray = jStruct.formatArray;
		Object[] result = new String[calculateSizeOfStringArray(formatArray)];
		int resPos = 0;
		int pos = 0;
		StringBuilder sb = new StringBuilder();

		for (int j = 0; j < formatArray.length; j++) {
			char type = formatArray[j];
			if (!formatCharacterSizes.keySet().contains(type)) {
				continue;
			}
			if (type == 'x') {
				pos++;
			}
			ByteBuffer bb = ByteBuffer.allocate(formatCharacterSizes.get(type));
			bb.order(jStruct.byteOrder);
			for (int x = 0; x < formatCharacterSizes.get(type); x++) {
				bb.put(buffer[pos++]);
			}
			if (type == 'c') {
				char c = bb.getChar();
				result[resPos++] = c;
			} else if (type == 'b' || type == 'B' || type == 'h' || type == 'H'
					|| type == 'i' || type == 'I' || type == 'l') {
				int i = bb.getInt();
				result[resPos++] = i;
			} else if (type == '?') {
				boolean b = !(bb.get() == 0);
				result[resPos++] = b;
			} else if (type == 'L' || type == 'q') {
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
				if (!(formatArray[j + 1] == 's')) {
					result[resPos++] = sb.toString();
					sb = new StringBuilder();
				}
			}
		}
		return result;
	}

	public static Object[] unpackFrom(String format, byte[] buffer, int offset)
			throws JStructException {
		return unpack(format, Arrays.copyOfRange(buffer, offset, buffer.length));
	}

	public static Object[] unpackFrom(String format, byte[] buffer)
			throws JStructException {
		return unpackFrom(format, buffer, 0);
	}

	public static Object[] iterUnpack(String format, byte[] buffer) {
		return new String[0];
	}

	public static int calculateSize(String format) {
		int size = 0;
		int count = 1;
		StringBuilder countReader = new StringBuilder();
		for (char c : format.toCharArray()) {
			if (Character.isDigit(c)) {
				countReader.append(c);
			} else if (formatCharacterSizes.keySet().contains(c)) {
				if (countReader.length() != 0) {
					count = Integer.parseInt(countReader.toString());
					countReader = new StringBuilder();
				}
				size += formatCharacterSizes.get(c) * count;
				count = 1;
			}
		}
		return size;
	}

	private static int calculateSizeOfStringArray(char[] formatArray) {
		int result = 0;
		for (char c : formatArray) {
			if (formatCharacterSizes.keySet().contains(c) && c != 'x') {
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
			} else if (formatCharacterSizes.keySet().contains(c)) {
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
		for (char c : formatCharacterSizes.keySet()) {
			sb.append(c);
		}
		sb.append("]+$");
		return format.matches(sb.toString());
	}

	@SuppressWarnings("serial")
	public static class JStructException extends Exception {

		public JStructException(String message) {
			super(message);
		}
	}
}
