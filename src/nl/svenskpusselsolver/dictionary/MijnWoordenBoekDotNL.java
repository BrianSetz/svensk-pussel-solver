package nl.svenskpusselsolver.dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.svenskpusselsolver.logging.Logger;
import nl.svenskpusselsolver.util.URLReader;

/**
 * Implementation of the PuzzelDictionary interface, uses MijnWoordenBoek.nl to
 * find answers.
 */
public class MijnWoordenBoekDotNL implements PuzzleDictionary {
	/**
	 * Return the answer(s) for a given word and length.
	 * 
	 * @param word
	 *            Word to find answers for.
	 * @param length
	 *            Length of the answer, -1 for no length.
	 * @return List of answers.
	 */
	public List<String> getAnswers(String word, int length) {
		Logger.log(Logger.DEBUG, "Downloading answers for " + word + " with a length of " + length +  ".");
		
		// Try to read the contents at the URL.
		String page = "";
		try {
			page = new URLReader().readURL(getConnectionURL(word, length));
		} catch (IOException e) {
			Logger.log(Logger.ERROR, "Downloading answers for " + word + " failed: " + e.getMessage() + ".");
			e.printStackTrace();
		}
		
		Pattern pagePattern = Pattern.compile("(background-color:#ddd>(<big){0})(((<font)|[^<]).*?)</td>");
		Pattern wordPattern = Pattern.compile("(\\s|>|^)([^ -<>]+?)(\\s|<|$)");
		
		Matcher matcher = pagePattern.matcher(page);

		List<String> answers = new ArrayList<String>();
		
		while (matcher.find()) {
			Matcher matcher2 = wordPattern.matcher(matcher.group(3));

			while (matcher2.find()) {
				String answer = matcher2.group(2).toUpperCase();
				
				// Replace IJ with Y
				String replacedAnswer = answer.replace("IJ", "Y");
				
				// Check if length is still the length we're looking for
				if(length == -1 || replacedAnswer.length() == length)
					answers.add(replacedAnswer);
			}
		}
		
		Logger.log(Logger.DEBUG, "Found " + answers.size() + " answers for " + word + " with a length of " + length +  ".");
		
		return answers;
	}

	/**
	 * Create a connection URL based on word and length.
	 * 
	 * @param word
	 *            Word to find answers for.
	 * @param length
	 *            Length of the answer, -1 for no length.
	 * @return Connection URL
	 */
	private String getConnectionURL(String word, int length) {
		word = word.replace(' ', '+');

		if (length > 0)
			return "http://www.mijnwoordenboek.nl/puzzelwoordenboek/?zoek="
					+ word + "&letters=" + length;
		else
			return "http://www.mijnwoordenboek.nl/puzzelwoordenboek/?zoek="
					+ word;
	}
}
