package za.ac.sun.cs.coastal;

import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utility class for creating more visible banners in the log output.
 */
public class Banner {

	/**
	 * The line separator used on this system.
	 */
	private static final String LS = System.getProperty("line.separator");

	/**
	 * The maximum width of banners.
	 */
	private static final int WIDTH = 70;

	/**
	 * The number of banner characters in the left and right borders.
	 */
	private static final int SIDE_WIDTH = 4;

	/**
	 * The number of spaces between the border and the banner content.
	 */
	private static final int SIDE_SPACE = 2;

	/**
	 * A writer used for recording the banner.
	 */
	protected final StringWriter bannerWriter = new StringWriter();

	/**
	 * A full border line for the top and bottom of the banner.
	 */
	private final String borderLine;

	/**
	 * The left-hand border.
	 */
	private final String borderLeft;

	/**
	 * The right-hand border.
	 */
	private final String borderRight;

	/**
	 * An empty line in the border for creating space between the top border and
	 * the content, or the content and the bottom border.
	 */
	private final String borderEmpty;

	/**
	 * A string builder that is used over and over for constructing the banner
	 * borders and content.
	 */
	private final StringBuilder sb = new StringBuilder();

	/**
	 * Construct a new banner. The main task of this routine is to create the
	 * border elements. It would be possible to cache constructed banners so
	 * that the borders do not have to be created every time. But banners are
	 * used infrequently, mostly during startup or termination, and it is not
	 * worth the effort.
	 * 
	 * @param borderChar
	 *            the character used for forming the border
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
	 * Add content to the banner.
	 * 
	 * @param message
	 *            the content to add
	 * @return the same instance of the banner so that these calls can be
	 *         chained
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
	 * Display the banner to the given log
	 * 
	 * @param log
	 *            the log
	 */
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

	/**
	 * Display the banner to the given writer.
	 * 
	 * @param out
	 *            the writer
	 */
	public void display(PrintWriter out) {
		out.println(borderLine + "\n" + borderLine + "\n" + borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			out.println(line);
		}
		out.println(borderEmpty + "\n" + borderLine + "\n" + borderLine);
	}

	/**
	 * Display the banner to the given stream.
	 * 
	 * @param out
	 *            the stream
	 */
	public void display(PrintStream out) {
		out.println(borderLine + "\n" + borderLine + "\n" + borderEmpty);
		for (String line : bannerWriter.toString().split(LS)) {
			out.println(line);
		}
		out.println(borderEmpty + "\n" + borderLine + "\n" + borderLine);
	}

	/**
	 * Produce a short, one-line banner.
	 * 
	 * @param string
	 *            the content of the banner
	 * @param bannerChar
	 *            the character to place around the content
	 * @return a one-line banner
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
	 * Format the elapsed time in human-readable form. It checks the duration
	 * and decides which units to include.
	 * 
	 * @param coastal
	 *            the instance of COASTAL for which to compute the elapsed time
	 * @return the nicely-formatted elapsed time
	 */
	public static String getElapsed(COASTAL coastal) {
		long t0 = coastal.getStartingTime();
		long dt = System.currentTimeMillis() - t0;
		long ms = dt % 1000;
		long sec = (dt / 1000) % 60;
		long min = (dt / 60000) % 60;
		long hr = dt / 3600000;
		if (hr != 0) {
			return String.format("%d:%02d:%02d.%02d", hr, min, sec, ms / 10);
		} else if (min != 0) {
			return String.format("%02d:%02d.%02d", min, sec, ms / 10);
		} else if (sec != 0) {
			return String.format("%d.%02d s", sec, ms / 10);
		} else {
			return String.format("%d ms", ms);
		}
	}

}
