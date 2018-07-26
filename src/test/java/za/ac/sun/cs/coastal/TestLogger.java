package za.ac.sun.cs.coastal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

public class TestLogger extends AbstractLogger {

	private static final boolean OUTPUT = true;

	private static final long serialVersionUID = -2826725115616515320L;

	private final ByteArrayOutputStream log = new ByteArrayOutputStream();
	private final PrintStream logOut = new PrintStream(log);
	private final PrintStream sysOut = System.out;

	@Override
	public boolean isEnabled(Level level, Marker marker, Message message, Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, CharSequence message, Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, Object message, Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Throwable t) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object... params) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
			Object p4) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
			Object p4, Object p5) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
			Object p4, Object p5, Object p6) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
			Object p4, Object p5, Object p6, Object p7) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
			Object p4, Object p5, Object p6, Object p7, Object p8) {
		return true;
	}

	@Override
	public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
			Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
		return true;
	}

	@Override
	public void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable t) {
		logOut.printf("%s\n", message.getFormattedMessage());
		if (OUTPUT) {
			sysOut.printf("%s\n", message.getFormattedMessage());
		}
	}

	@Override
	public Level getLevel() {
		return Level.ALL;
	}

	@Override
	public String toString() {
		return log.toString();
	}

}
