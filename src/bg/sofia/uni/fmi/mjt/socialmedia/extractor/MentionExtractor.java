package bg.sofia.uni.fmi.mjt.socialmedia.extractor;

import bg.sofia.uni.fmi.mjt.socialmedia.user.User;
import bg.sofia.uni.fmi.mjt.socialmedia.validator.Validator;

import java.util.HashSet;
import java.util.Set;

public class MentionExtractor {
    private static final String MENTION_SYMBOL = "@";
    private static final String MULTIPLE_WHITESPACE_PATTERN = "\\s+";

    public static Set<User> extract(String description) {
        Validator.validateNotNull(description, "description");

        Set<User> mentions = new HashSet<>();
        String[] words = description.split(MULTIPLE_WHITESPACE_PATTERN);

        for (String word : words) {
            if (word.startsWith(MENTION_SYMBOL)) {
                User user = extractUsername(word);
                mentions.add(user);
            }
        }

        return mentions;
    }

    private static User extractUsername(String word) {
        int endIndex = word.length();

        while (endIndex > 0 && !Character.isLetterOrDigit(word.charAt(endIndex - 1))) {
            endIndex--;
        }

        String username = word.substring(1, endIndex);
        return new User(username);
    }
}
