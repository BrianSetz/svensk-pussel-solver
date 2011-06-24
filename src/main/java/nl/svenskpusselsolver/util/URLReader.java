package nl.svenskpusselsolver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class URLReader {
	private final static Logger logger = Logger.getLogger(URLReader.class);
	
	/**
	 * Read contents at URL
	 * 
	 * @param urlString URL to read from
	 * @return Contents at URL
	 * @throws IOException
	 */
	public String readURL(String urlString) throws IOException {
		logger.debug("Connecting to " + urlString + ".");
		
		// Connect to URL
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();

		// Read contents
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF8"));
		StringBuilder result = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null)
			result.append(line);

		in.close();

		logger.trace("Got response from " + urlString + ".");
		
		return result.toString();
	}
}
