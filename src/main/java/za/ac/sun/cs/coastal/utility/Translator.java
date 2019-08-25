/**
 * 
 */
package za.ac.sun.cs.coastal.utility;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Translator {

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

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	/**
	 * Helper for non-Writer usage.
	 * 
	 * @param input
	 *              CharSequence to be translated
	 * @return String output of translation
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
	 * Translate an input onto a Writer. This is intentionally final as its
	 * algorithm is tightly coupled with the abstract method of this class.
	 *
	 * @param input
	 *              CharSequence that is being translated
	 * @param out
	 *              Writer to translate the text to
	 * @throws IOException
	 *                     if and only if the Writer produces an IOException
	 */
	private static void translate(final CharSequence input, final Writer out) throws IOException {
		if (out == null) {
			throw new IllegalArgumentException("The Writer must not be null");
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
