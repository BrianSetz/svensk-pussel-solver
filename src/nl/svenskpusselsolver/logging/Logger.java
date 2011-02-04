package nl.svenskpusselsolver.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public static final int FATAL = 0;
	public static final int ERROR = 1;
	public static final int WARNING = 2;
	public static final int INFO = 3;
	public static final int DEBUG = 4;
	public static final int TRACE = 5;
	
	public static final int level = DEBUG;
	
	public static void log(int logLevel, String message) {	    
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
	    String dateString = dateFormat.format(date);
	    
	    if(logLevel <= level)
	    	System.out.println(dateString + " - " + logLevelToString(logLevel) + " - " + message);
	}

	private static String logLevelToString(int logLevel) {
		switch(logLevel) {
		case FATAL:
			return "FATAL";
		case ERROR:
			return "ERROR";
		case WARNING:
			return "WARNING";			
		case INFO:
			return "INFO";
		case DEBUG:
			return "DEBUG";
		case TRACE:
			return "TRACE";
		}
		
		return "UNKNOWN";
	}
}
