package nl.svenskpusselsolver.dictionary;

import java.util.List;

/**
 * Dictionary interface to return answers for a given word and length.
 */
public interface PuzzleDictionary {
    /**
     * Return the answer(s) for a given word and length.
     * @param word Word to find answers for.
     * @param length Length of the answer, -1 for no length.
     * @return List of answers.
     */
	public List<String> getAnswers(String word, int length);
}
