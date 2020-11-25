import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {

    private static final char SPECIAL_SEARCH_CHAR = 8; // backspace char

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.println("You must provide exactly 2 parameters");
        } else {
            SubstringFinder finder = new SubstringFinder();
            boolean isSubstring = finder.isSubstring(args[0], args[1]);
            System.out.println("Text \"" + args[0] + "\" " + (isSubstring ? "does not contain" : "contains") + " substring \"" + args[1] + "\"");
        }
    }

    public boolean isSubstring(String text, String searchPhrase) {
        if (searchPhrase.length() == 0) {
            return true;
        }
        String normalizeSearchPhrase = normalizeSearchPhrase(searchPhrase);
        if (normalizeSearchPhrase.length() == 1 && normalizeSearchPhrase.charAt(0) == SPECIAL_SEARCH_CHAR) {
            return true;
        }
        List<String> searchPhrases = toSearchPhrases(searchPhrase);
        return find(text.toCharArray(), searchPhrases, 0, 0);
    }

    private boolean find(char[] text, List<String> searchPhrases, int from, int searchPhraseIdx) {
        char[] searchPhraseChars = searchPhrases.get(searchPhraseIdx).toCharArray();
        char first = searchPhraseChars[0];
        for (int i = from; i < text.length; i++) {
            if (!compareChars(text[i], first)) {
                do {
                    i++;
                } while (i < text.length && !compareChars(text[i], first));
            }

            if (i < text.length) {
                int j = i;
                int k = 0;

                for (; k < searchPhraseChars.length; ++k) {
                    if (j >= text.length || !compareChars(text[j], searchPhraseChars[k])) {
                        break;
                    }
                    ++j;
                }

                if (k == searchPhraseChars.length) {
                    if (searchPhraseIdx + 1 < searchPhrases.size()) {
                        return find(text, searchPhrases, j, ++searchPhraseIdx);
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Normalize given searchPhrase. First method replaces:
     * - sequence of chars '\' and '*' by '*',
     * - char '*' by SPECIAL_SEARCH_CHAR.
     * Then it replace multiple occurrences of SPECIAL_SEARCH_CHAR in a row, by one SPECIAL_SEARCH_CHAR.
     *
     * @param searchPhrase
     * @return
     */
    private static String normalizeSearchPhrase(String searchPhrase) {
        StringBuilder builder = new StringBuilder(searchPhrase.length());
        char previousChar = '1';
        for (int i = 0; i < searchPhrase.length(); i++) {
            char currentChar = searchPhrase.charAt(i);
            if (searchPhrase.charAt(i) == '\\' && i + 1 < searchPhrase.length() && searchPhrase.charAt(i + 1) == '*') {
                currentChar = '*';
                i++;
            } else if (searchPhrase.charAt(i) == '*') {
                currentChar = SPECIAL_SEARCH_CHAR;
            }
            if (currentChar != SPECIAL_SEARCH_CHAR || previousChar != SPECIAL_SEARCH_CHAR) {
                builder.append(currentChar);
            }
            previousChar = currentChar;
        }
        return builder.toString();
    }

    private static List<String> toSearchPhrases(String searchPhrase) {
        List<String> result = new ArrayList<>(searchPhrase.length());
        StringBuilder builder = new StringBuilder(searchPhrase.length());
        for (int i = 0; i < searchPhrase.length(); i++) {
            char currentChar = searchPhrase.charAt(i);
            if (searchPhrase.charAt(i) == '\\' && searchPhrase.length() > i + 1 && searchPhrase.charAt(i + 1) == '*') {
                builder.append('*');
                i++;
            } else if (searchPhrase.charAt(i) == '*') {
                if (builder.length() > 0) {
                    result.add(builder.toString());
                    builder = new StringBuilder();
                }
            } else {
                builder.append(currentChar);
            }
        }
        if (builder.length() > 0) {
            result.add(builder.toString());
        }
        return result;
    }

    private boolean compareChars(char original, char pattern) {
        return original == pattern || pattern == SPECIAL_SEARCH_CHAR;
    }
}
