public class SubstringFinder {

    private static final char SPECIAL_SEARCH_CHAR = 8; // backspace char

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.println("You must provide exactly 2 parameters");
        } else {
            SubstringFinder finder = new SubstringFinder();
            int index = finder.find(args[0], args[1]);
            System.out.println("Text \"" + args[0] + "\" " + (index == -1 ? "does not contain" : "contains") + " substring \"" + args[1] + "\"");
        }
    }

    public int find(String text, String search) {
        char[] textChars = text.toCharArray();
        char[] searchPhraseChars = normalizeSearchPhrase(search).toCharArray();
        int max = textChars.length;
        char first = searchPhraseChars[0];
        for (int i = 0; i < max; i++) {
            if (!compareChars(textChars[i], first)) {
                do {
                    i++;
                } while (i < max && !compareChars(textChars[i], first));
            }

            if (i < max) {
                int j = i;
                int k = 0;

                for (; k < searchPhraseChars.length; ++k) {
                    if (searchPhraseChars[k] == SPECIAL_SEARCH_CHAR && k + 1 < searchPhraseChars.length) {
                        k++;
                        while (j < textChars.length && !compareChars(textChars[j], searchPhraseChars[k])) {
                            j++;
                        }
                    }
                    if (j >= textChars.length || !compareChars(textChars[j], searchPhraseChars[k])) {
                        break;
                    }
                    ++j;
                }

                if (k == searchPhraseChars.length) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Normalize given searchPhrase. First method replaces:
     * - sequence of chars '\' and '*' by '*',
     * - char '*' by SPECIAL_SEARCH_CHAR.
     * Then it replace multiple occurrences of SPECIAL_SEARCH_CHAR in a row, by one SPECIAL_SEARCH_CHAR. And at the end
     * it removes trailing SPECIAL_SEARCH_CHARs.
     *
     * @param searchPhrase
     * @return
     */
    private static String normalizeSearchPhrase(String searchPhrase) {
        StringBuilder builder = new StringBuilder(searchPhrase.length());
        char previousChar = '1';
        for (int i = 0; i < searchPhrase.length(); i++) {
            char currentChar = searchPhrase.charAt(i);
            if (searchPhrase.charAt(i) == '\\' && searchPhrase.charAt(i + 1) == '*') {
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
        if (builder.length() > 1) {
            if (builder.charAt(0) == SPECIAL_SEARCH_CHAR) {
                builder.deleteCharAt(0);
            }
            if (builder.charAt(builder.length() - 1) == SPECIAL_SEARCH_CHAR) {
                builder.deleteCharAt(builder.length() - 1);
            }
        }
        return builder.toString();
    }

    private boolean compareChars(char original, char pattern) {
        return original == pattern || pattern == SPECIAL_SEARCH_CHAR;
    }
}
