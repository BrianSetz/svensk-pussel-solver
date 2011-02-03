package nl.svenskpusselsolver.dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.svenskpusselsolver.util.URLReader;

/**
 * Implementation of the PuzzelDictionary inteface, uses MijnWoordenBoek.nl to
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
		// Try to read the contents at the URL.
		String page = "";
		try {
			page = new URLReader().readURL(getConnectionURL(word, length));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Pattern pattern = Pattern.compile("[^;]<font color=navy>(.*?)<");
		Matcher matcher = pattern.matcher(page);

		List<String> answers = new ArrayList<String>();
		
		while (matcher.find()) {
			answers.add(matcher.group(1));
		}

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
