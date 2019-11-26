/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.utility;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Various methods for string translations.
 */
public class Translator {

	/**
	 * Mapping of source characters to translated characters.
	 */
	private static final Map<Character, String> TRANSLATE_MAP = new HashMap<>();

	static {
		TRANSLATE_MAP.put('"', "\\\"");
		TRANSLATE_MAP.put('\\', "\\\\");
		TRANSLATE_MAP.put('\b', "\\b");
		TRANSLATE_MAP.put('\f', "\\f");
		TRANSLATE_MAP.put('\n', "\\n");
		TRANSLATE_MAP.put('\r', "\\r");
		TRANSLATE_MAP.put('\t', "\\t");
	}

	/**
	 * Hexadecimal digits.
	 */
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	/**
	 * Helper for non-Writer usage.
	 * 
	 * @param input
	 *              sequence of characters to be translated
	 * @return translated string
	 */
	public static String translate(final CharSequence input) {
		if (input == null) {
			return null;
		}
		try {
			final StringWriter writer = new StringWriter(input.length() * 2);
			translate(input, writer);
			return writer.toString();
		} catch (final IOException ioe) {
			// this should never ever happen while writing to a StringWriter
			throw new RuntimeException(ioe);
		}
	}

	/**
	 * Translate an input string and write it to a writer. This method handles
	 * Unicode characters.
	 *
	 * @param input
	 *              string that is being translated
	 * @param out
	 *              writer to translate the text to
	 * @throws IOException
	 *                     if the writer produces an IOException
	 */
	private static void translate(final CharSequence input, final Writer out) throws IOException {
		if (out == null) {
			throw new IllegalArgumentException("The output writer must not be null");
		}
		if (input == null) {
			return;
		}
		int pos = 0;
		final int len = input.length();
		while (pos < len) {
			final int consumed = translate(input, pos, out);
			if (consumed == 0) {
				final char c1 = input.charAt(pos);
				out.write(c1);
				pos++;
				if (Character.isHighSurrogate(c1) && pos < len) {
					final char c2 = input.charAt(pos);
					if (Character.isLowSurrogate(c2)) {
						out.write(c2);
						pos++;
					}
				}
				continue;
			}
			for (int pt = 0; pt < consumed; pt++) {
				pos += Character.charCount(Character.codePointAt(input, pos));
			}
		}
	}

	/**
	 * Translate a special character at a given index of a string and output it to a
	 * writer.
	 *
	 * @param input
	 *              input string
	 * @param index
	 *              index of special character
	 * @param out
	 *              writer to output to
	 * @return 0 if the character is not special, 1 if special character was
	 *         processed
	 * @throws IOException
	 *                     if the writer produces an IOException
	 */
	private static int translate(final CharSequence input, final int index, final Writer out) throws IOException {
		char ch = input.charAt(index);
		if (TRANSLATE_MAP.containsKey(ch)) {
			out.write(TRANSLATE_MAP.get(ch));
			return 1;
		}
		if ((ch < 32) || (ch > 0x7f)) {
			out.write("\\u");
			out.write(HEX_DIGITS[(ch >> 12) & 15]);
			out.write(HEX_DIGITS[(ch >> 8) & 15]);
			out.write(HEX_DIGITS[(ch >> 4) & 15]);
			out.write(HEX_DIGITS[(ch) & 15]);
			return 1;
		}
		return 0;
	}

}
