package za.ac.sun.cs.coastal.reporting;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Class with static routine to display a banner.
 */
public class Banner {

	/**
	 * Line separator.
	 */
	private static final String LS = System.getProperty("line.separator");

	/**
	 * Width of the banner.
	 */
	private static final int WIDTH = 70;

	/**
	 * Width of the side of the banner.
	 */
	private static final int SIDE_WIDTH = 4;

	/**
	 * Width of the spaces on the side of the banner.
	 */
	private static final int SIDE_SPACE = 2;

	/**
	 * Collector of banner content.
	 */
	protected final StringWriter bannerWriter = new StringWriter();

	/**
	 * Horizontal banner line.
	 */
	private final String borderLine;

	/**
	 * Left-hand side of middle of banner.
	 */
	private final String borderLeft;

	/**
	 * Right-hand side of middle of banner.
	 */
	private final String borderRight;

	/**
	 * Empty line in middle of banner.
	 */
	private final String borderEmpty;

	/**
	 * Auxiliary buffer used to store the banner.
	 */
	private final StringBuilder sb = new StringBuilder();

	/**
	 * Constructs the banner. The only task to initialize the border lines.
	 *
	 * @param borderChar
	 *            the character used to construct the banner
	 */
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

	/**
	 * Adds a message to the banner.
	 *
	 * @param message
	 *            banner content
	 * @return the banner instance
	 */
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

	/**
	 * Displays the banner to the given log with the given level.
	 *
	 * @param lgr
	 *            destination log for the banner
	 * @param level
	 *            log level for the banner
	 */
	public void display(Logger lgr) {
		lgr.log(Level.INFO, borderLine);
		lgr.log(Level.INFO, borderLine);
		lgr.log(Level.INFO, borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			lgr.log(Level.INFO, line);
		}
		lgr.log(Level.INFO, borderEmpty);
		lgr.log(Level.INFO, borderLine);
		lgr.log(Level.INFO, borderLine);
	}

	/**
	 * Displays the banner to the given writer.
	 *
	 * @param out
	 *            destination for the banner
	 */
	public void display(PrintWriter out) {
		out.println(borderLine + "\n" + borderLine + "\n" + borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			out.println(line);
		}
		out.println(borderEmpty + "\n" + borderLine + "\n" + borderLine);
	}

	/**
	 * Displays the banner to the given stream.
	 *
	 * @param out
	 *            destination for the banner
	 */
	public void display(PrintStream out) {
		out.println(borderLine + "\n" + borderLine + "\n" + borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			out.println(line);
		}
		out.println(borderEmpty + "\n" + borderLine + "\n" + borderLine);
	}

	/**
	 * Displays a one-line banner with an arbitrary string.
	 *
	 * @param string
	 *            the string to place in the banner
	 * @param bannerChar
	 *            the character to use for the banner
	 */
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

	/**
	 * Displays a one-line banner with the name of a reporter.
	 *
	 * @param reporter
	 *            the reporter to produce the banner for
	 * @param bannerChar
	 *            the character to use for the banner
	 * @param lgr
	 *            the destination logger for the banner
	 */
	public static String getBannerLine(Reporter reporter, char bannerChar) {
		return getBannerLine(reporter.getName(), bannerChar);
	}

}
