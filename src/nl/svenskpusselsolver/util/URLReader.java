package nl.svenskpusselsolver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import nl.svenskpusselsolver.logging.Logger;

public class URLReader {
	/**
	 * Read contents at URL
	 * 
	 * @param urlString
	 *            URL to read from
	 * @return Contents at URL
	 * @throws IOException
	 */
	public String readURL(String urlString) throws IOException {
		Logger.log(Logger.LogLevel.DEBUG, "Connecting to " + urlString + ".");
		
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

		Logger.log(Logger.LogLevel.TRACE, "Got response from " + urlString + ".");
		
		return result.toString();
	}
}
