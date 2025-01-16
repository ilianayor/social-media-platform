package bg.sofia.uni.fmi.mjt.socialmedia.tracker;

import bg.sofia.uni.fmi.mjt.socialmedia.comparator.MentionsComparator;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.NoMentionsFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;
import bg.sofia.uni.fmi.mjt.socialmedia.validator.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MentionsTracker {
    private final Map<User, Set<User>> mentions;

    public MentionsTracker() {
        this.mentions = new HashMap<>();
    }

    public void updateMentions(Set<User> mentionedUsers, User mentioningUser) {
        Validator.validateNotNull(mentionedUsers, "mentionedUsers");
        Validator.validateNotNull(mentioningUser, "mentioningUser");

        for (User mentionedUser : mentionedUsers) {
            mentions.putIfAbsent(mentionedUser, new HashSet<>());
            mentions.get(mentionedUser).add(mentioningUser);
        }
    }

    public User getMostPopularUser() {
        if (mentions.isEmpty()) {
            throw new NoMentionsFoundException("No mentions available to determine the most popular user!");
        }

        List<Map.Entry<User, Set<User>>> entries = new ArrayList<>(mentions.entrySet());
        entries.sort(new MentionsComparator());

        return entries.get(0).getKey();
    }
}
