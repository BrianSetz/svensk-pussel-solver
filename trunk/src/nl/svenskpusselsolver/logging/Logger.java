package nl.svenskpusselsolver.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public enum LogLevel {
		FATAL, ERROR, WARNING, INFO, DEBUG, TRACE
	}

	public static final LogLevel level = LogLevel.DEBUG;

	public static void log(LogLevel logLevel, String message) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);

		if (logLevel.ordinal() <= level.ordinal())
			System.out.println(dateString + " - " + logLevel + " - " + message);
	}
}
