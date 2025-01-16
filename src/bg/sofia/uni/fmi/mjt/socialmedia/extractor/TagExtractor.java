package bg.sofia.uni.fmi.mjt.socialmedia.extractor;

import bg.sofia.uni.fmi.mjt.socialmedia.validator.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class TagExtractor {
    private static final String HASHTAG_SYMBOL = "#";
    private static final String MULTIPLE_WHITESPACE_PATTERN = "\\s+";

    public static Collection<String> extract(String description) {
        Validator.validateNotNull(description, "description");

        Collection<String> tags = new ArrayList<>();
        String[] words = description.split(MULTIPLE_WHITESPACE_PATTERN);

        for (String word : words) {
            if (word.startsWith(HASHTAG_SYMBOL)) {
                String tag = extractTag(word);
                tags.add(tag);
            }
        }

        return tags;
    }

    private static String extractTag(String word) {
        int endIndex = word.length();

        while (endIndex > 0 && !Character.isLetterOrDigit(word.charAt(endIndex - 1))) {
            endIndex--;
        }

        return word.substring(0, endIndex);
    }
}

