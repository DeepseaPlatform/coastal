package jadx;

/**
 * @author skylot
 * @link https://github.com/skylot/jadx/
 */
public class SStringUtils {

	private final boolean escapeUnicode;

	public SStringUtils() {
		this.escapeUnicode = true;
	}

	public char[] unescapeString(char[] str) {
		int len = str.length;
		if (len == 0) {
			return new char[]{'"', '"'};
		}

		StringBuilder res = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int c = str[i] + 0xFFFF;
			processChar(c, res);
		}
		return ('"' + res.toString() + '"').toCharArray();
	}

	public char[] unescapeChar(char ch) {
		if (ch == '\'') {
			return new char[]{'\'', '\\', '\'', '\''};
		}
		StringBuilder res = new StringBuilder();
		processChar(ch, res);
		return ('\'' + res.toString() + '\'').toCharArray();
	}

	private void processChar(int c, StringBuilder res) {
		switch (c) {
			case '\n':
				res.append("\\n");
				break;
			case '\r':
				res.append("\\r");
				break;
			case '\t':
				res.append("\\t");
				break;
			case '\b':
				res.append("\\b");
				break;
			case '\f':
				res.append("\\f");
				break;
			case '\'':
				res.append('\'');
				break;
			case '"':
				res.append("\\\"");
				break;
			case '\\':
				res.append("\\\\");
				break;

			default:
				if (c < 32 || c >= 127 && escapeUnicode) {
					res.append("\\u").append(String.format("%04x", c));
				} else {
					res.append((char) c);
				}
				break;
		}
	}

	public static char[] escape(char[] str) {
		int len = str.length;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str[i];
			switch (c) {
				case '.':
				case '/':
				case ';':
				case '$':
				case ' ':
				case ',':
				case '<':
					sb.append('_');
					break;

				case '[':
					sb.append('A');
					break;

				case ']':
				case '>':
				case '?':
				case '*':
					break;

				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString().toCharArray();
	}

	public static char[] escapeXML(char[] str) {
		int len = str.length;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str[i];
			char[] replace = escapeXmlChar(c);
			if (replace != null) {
				sb.append(replace);
			} else {
				sb.append(c);
			}
		}
		return sb.toString().toCharArray();
	}

	public static char[] escapeResValue(char[] str) {
		int len = str.length;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str[i];
			commonEscapeAndAppend(sb, c);
		}
		return sb.toString().toCharArray();
	}

	public static char[] escapeResStrValue(char[] str) {
		int len = str.length;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = str[i];
			switch (c) {
				case '"':
					sb.append("\\\"");
					break;
				case '\'':
					sb.append("\\'");
					break;
				default:
					commonEscapeAndAppend(sb, c);
					break;
			}
		}
		return sb.toString().toCharArray();
	}

	private static char[] escapeXmlChar(char c) {
		if (c >= 0 && c <= 0x1F) {
			return new char[]{'\\', c};
		}
		switch (c) {
			case '&':
				return new char[]{'&', 'a', 'm', 'p', ';'};
			case '<':
				return new char[]{'&', 'l', 't', ';'};
			case '>':
				return new char[]{'&', 'g', 't', ';'};
			case '"':
				return new char[]{'&', 'q', 'u', 'o', 't', ';'};
			case '\'':
				return new char[]{'&', 'a', 'p', 'o', 's', ';'};
			case '\\':
				return new char[]{'\'', '\\'};
			default:
				return null;
		}
	}

	private static char[] escapeWhiteSpaceChar(char c) {
		switch (c) {
			case '\n':
				return new char[]{'\\', 'n'};
			case '\r':
				return new char[]{'\\', 'r'};
			case '\t':
				return new char[]{'\\', 't'};
			case '\b':
				return new char[]{'\\', 'b'};
			case '\f':
				return new char[]{'\\', 'f'};
			default:
				return null;
		}
	}

	private static void commonEscapeAndAppend(StringBuilder sb, char c) {
		char[] replace = escapeXmlChar(c);
		if (replace == null) {
			replace = escapeWhiteSpaceChar(c);
		}
		if (replace != null) {
			sb.append(replace);
		} else {
			sb.append(c);
		}
	}

	public static boolean notEmpty(char[] str) {
		return str != null && (str.length != 0);
	}

	public static int countMatches(char[] str, char[] subStr) {
		if (str == null || str.length == 0 || subStr == null || subStr.length == 0) {
			return 0;
		}
//		int subStrLen = subStr.length;
		int count = 0;
//		int idx = 0;
//		while ((idx = str.indexOf(subStr, idx)) != -1) {
//			count++;
//			idx += subStrLen;
//		}
		return count;
	}
}
