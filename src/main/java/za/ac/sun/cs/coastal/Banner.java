package za.ac.sun.cs.coastal;

import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Banner {

	private static final String LS = System.getProperty("line.separator");

	private static final int WIDTH = 70;

	private static final int SIDE_WIDTH = 4;

	private static final int SIDE_SPACE = 2;

	protected final StringWriter bannerWriter = new StringWriter();

	private final String borderLine;

	private final String borderLeft;

	private final String borderRight;

	private final String borderEmpty;

	private final StringBuilder sb = new StringBuilder();

	public Banner(char borderChar) {
		// Construct "====================="
		sb.setLength(0);
		while (sb.length() < WIDTH) {
			sb.append(borderChar);
		}
		borderLine = sb.toString();
		// Construct "====  "
		sb.setLength(0);
		for (int i = 0; i < SIDE_WIDTH; i++) {
			sb.append(borderChar);
		}
		for (int i = 0; i < SIDE_SPACE; i++) {
			sb.append(' ');
		}
		borderLeft = sb.toString();
		// Construct "  ===="
		sb.setLength(0);
		for (int i = 0; i < SIDE_SPACE; i++) {
			sb.append(' ');
		}
		for (int i = 0; i < SIDE_WIDTH; i++) {
			sb.append(borderChar);
		}
		borderRight = sb.toString();
		// Construct "====             ===="
		sb.setLength(0);
		sb.append(borderLeft);
		while (sb.length() < WIDTH - SIDE_WIDTH - SIDE_SPACE) {
			sb.append(' ');
		}
		sb.append(borderRight);
		borderEmpty = sb.toString();
	}

	public Banner println(String message) {
		for (String line : message.split("\n")) {
			sb.setLength(0);
			sb.append(borderLeft).append(line);
			if (sb.length() <= WIDTH - SIDE_WIDTH - SIDE_SPACE) {
				while (sb.length() < WIDTH - SIDE_WIDTH - SIDE_SPACE) {
					sb.append(' ');
				}
				sb.append(borderRight);
			}
			bannerWriter.append(sb.append(LS).toString());
		}
		return this;
	}

	public void display(Logger log) {
		log.info(borderLine);
		log.info(borderLine);
		log.info(borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			log.info(line);
		}
		log.info(borderEmpty);
		log.info(borderLine);
		log.info(borderLine);
	}

	public void display(PrintWriter out) {
		out.println(borderLine + "\n" + borderLine + "\n" + borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			out.println(line);
		}
		out.println(borderEmpty + "\n" + borderLine + "\n" + borderLine);
	}

	public void display(PrintStream out) {
		out.println(borderLine + "\n" + borderLine + "\n" + borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			out.println(line);
		}
		out.println(borderEmpty + "\n" + borderLine + "\n" + borderLine);
	}

	public static String getBannerLine(String string, char bannerChar) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIDE_WIDTH; i++) {
			sb.append(bannerChar);
		}
		for (int i = 0; i < SIDE_SPACE; i++) {
			sb.append(' ');
		}
		sb.append(string);
		if (sb.length() <= WIDTH - SIDE_SPACE - 1) {
			for (int i = 0; i < SIDE_SPACE; i++) {
				sb.append(' ');
			}
			while (sb.length() < WIDTH) {
				sb.append(bannerChar);
			}
		}
		return sb.toString();
	}

}
