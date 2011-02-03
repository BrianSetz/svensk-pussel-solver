package nl.svenskpusselsolver.woordenboek;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import nl.svenskpusselsolver.util.URLReader;

/**
 * Implementation of the PuzzelDictionary inteface, uses MijnWoordenBoek.nl to find answers.
 */
public class MijnWoordenBoekDotNL implements PuzzelDictionary {
    /**
     * Return the answer(s) for a given word and length.
     * @param word Word to find answers for.
     * @param length Length of the answer, -1 for no length.
     * @return List of answers.
     */
	public List<String> getAnswers(String word, int length) {
        // Try to read the contents at the URL.
		String result = "";
		try {
			result = new URLReader().readURL(getConnectionURL(word, length));
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Seperate the words from the rest of the HTML.
		result = getRawWordsFromHTML(result);

        // Strip HTML from words, different HTML is found when searching with no length.
		if(length > 0) {
			result = stripHTMLTagsSingleResult(result);
		} else {
			result = stripHTMLTagsMultipleResults(result);
		}

        // Split and return as list.
		return Arrays.asList(result.split(" - "));
	}

    /**
     * Create a connection URL based on word and length.
     * @param word Word to find answers for.
     * @param length Length of the answer, -1 for no length.
     * @return Connection URL
     */
	private String getConnectionURL(String word, int length) {
		word = word.replace(' ', '+');
		
		if(length > 0)
			return "http://www.mijnwoordenboek.nl/puzzelwoordenboek/?zoek=" + word + "&letters=" + length;
		else
			return "http://www.mijnwoordenboek.nl/puzzelwoordenboek/?zoek=" + word;
	}

    /**
     * Separates the answers from the rest of the HTML string.
     * @param html HTML to parse.
     * @return Words extracted from HTML.
     */
	private String getRawWordsFromHTML(String html) {
        // Find the answers table.
		String beginIndexString = "<table style=width:720px>";

        // Cut everything away in front of the table.
		int beginIndex = html.indexOf(beginIndexString);
		html = html.substring(beginIndex + beginIndexString.length());

        // Cut everything away at the end of the table.
		int endIndex = html.indexOf("</table>");
		html = html.substring(0, endIndex);
		
		return html;
	}

    /**
     *  Strip HTML from words when using a length other than -1.
     * @param html HTML to parse.
     * @return Words without HTML.
     */
	private String stripHTMLTagsSingleResult(String html) {
        // While there are HTML tags.
		while(html.indexOf("<") != -1) {
            // Remove tags.
			int begin = html.indexOf('<');
			int end = html.indexOf('>');
			
			String before = html.substring(0, begin);
			String after = html.substring(end + 1, html.length());
			
			html = before + after;
		}	
		
		return html;
	}

    /**
     *  Strip HTML from words when using length -1
     * @param html HTML to parse
     * @return Words without HTML.
     */
	private String stripHTMLTagsMultipleResults(String html) {
        // Add a separator at the end of each </td>
		html = html.replace("</td>", " - </td>");

        // While there are HTML tags.
		int tdCount = 0;
		while(html.indexOf("<") != -1) {
            // Find tag
			int begin = html.indexOf('<');
			int end = html.indexOf('>');

            // Check for <td> to count which column we are processing.
			String between = html.substring(begin, end + 1);
			if(between.contains("td") && !between.contains("/td"))
				tdCount++;

            // If we are processing the "letters" column, delete the whole column
			if(tdCount % 2 != 0)
                // Look for the </td> tag
				end = html.indexOf("</td>", end + 1) + 4;

            // Strip tag
			String before = html.substring(0, begin);
			String after = html.substring(end + 1, html.length());
			
			html = before + after;
		}	
		
		return html;
	}
}
