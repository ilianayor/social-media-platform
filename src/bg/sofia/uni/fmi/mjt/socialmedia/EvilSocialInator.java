package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.activity.Activity;
import bg.sofia.uni.fmi.mjt.socialmedia.activity.CommentActivity;
import bg.sofia.uni.fmi.mjt.socialmedia.activity.LikeActivity;
import bg.sofia.uni.fmi.mjt.socialmedia.activity.PostActivity;
import bg.sofia.uni.fmi.mjt.socialmedia.activity.StoryActivity;
import bg.sofia.uni.fmi.mjt.socialmedia.comparator.ActivityByDateComparator;
import bg.sofia.uni.fmi.mjt.socialmedia.comparator.ContentByDateComparator;
import bg.sofia.uni.fmi.mjt.socialmedia.comparator.ContentByPopularityComparator;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentHandler;
import bg.sofia.uni.fmi.mjt.socialmedia.enums.ContentType;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.NoUsersException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.factory.ContentFactory;
import bg.sofia.uni.fmi.mjt.socialmedia.factory.DefaultContentFactory;
import bg.sofia.uni.fmi.mjt.socialmedia.tracker.MentionsTracker;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;
import bg.sofia.uni.fmi.mjt.socialmedia.validator.Validator;
import bg.sofia.uni.fmi.mjt.socialmedia.extractor.MentionExtractor;
import bg.sofia.uni.fmi.mjt.socialmedia.extractor.TagExtractor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EvilSocialInator implements SocialMediaInator {
    private static final String DASH = "-";
    private static int currentIdNumber = 0;
    private final Map<User, List<Activity>> users;
    private final Collection<ContentHandler> content;
    private final MentionsTracker mentionsTracker;
    private final ContentFactory contentFactory;

    public EvilSocialInator() {
        this.users = new HashMap<>();
        this.content = new LinkedList<>();
        this.mentionsTracker = new MentionsTracker();
        this.contentFactory = new DefaultContentFactory();
    }

    public Set<User> getUsers() {
        return users.keySet();
    }

    @Override
    public void register(String username) {
        Validator.validateNotNull(username, "username");

        User user = new User(username);

        if (users.containsKey(user)) {
            throw new UsernameAlreadyExistsException(
                "User with username : [" + username + "] already exists!");
        }

        users.put(user, new LinkedList<>());
    }

    @Override
    public String publishPost(String username, LocalDateTime publishedOn, String description) {
        Validator.validateNotNull(username, "username");
        Validator.validateNotNull(publishedOn, "publishedOn");
        Validator.validateNotNull(description, "description");

        User user = new User(username);

        if (!users.containsKey(user)) {
            throw new UsernameNotFoundException(
                "User with username : [" + username + "] doesn't exist!");
        }

        String postId = generateId(username);
        Collection<String> tags = extractTags(description);
        Collection<String> mentions = extractMentions(description, username);
        ContentHandler post = contentFactory.createContent(postId, publishedOn, user, tags, mentions, ContentType.POST);
        content.add(post);

        Activity activity = new PostActivity(publishedOn, post);
        users.get(user).add(activity);

        return postId;
    }

    @Override
    public String publishStory(String username, LocalDateTime publishedOn, String description) {
        Validator.validateNotNull(username, "username");
        Validator.validateNotNull(publishedOn, "publishedOn");
        Validator.validateNotNull(description, "description");

        User user = new User(username);

        if (!users.containsKey(user)) {
            throw new UsernameNotFoundException(
                "User with username : [" + username + "] doesn't exist!");
        }

        String storyId = generateId(username);
        Collection<String> tags = extractTags(description);
        Collection<String> mentions = extractMentions(description, username);
        ContentHandler story =
            contentFactory.createContent(storyId, publishedOn, user, tags, mentions, ContentType.STORY);

        Activity activity = new StoryActivity(publishedOn, story);
        users.get(user).add(activity);

        return storyId;
    }

    @Override
    public void like(String username, String id) {
        Validator.validateNotNull(username, "username");
        Validator.validateNotNull(id, "id");

        User user = new User(username);

        if (!users.containsKey(user)) {
            throw new UsernameNotFoundException(
                "User with username : [" + username + "] doesn't exist!");
        }

        ContentHandler content = getContentById(id);
        content.increaseLikes();

        users.get(user).add(new LikeActivity(id));
    }

    @Override
    public void comment(String username, String text, String id) {
        Validator.validateNotNull(username, "username");
        Validator.validateNotNull(text, "text");
        Validator.validateNotNull(id, "id");

        User user = new User(username);

        if (!users.containsKey(user)) {
            throw new UsernameNotFoundException(
                "User with username : [" + username + "] doesn't exist!");
        }

        ContentHandler content = getContentById(id);
        content.increaseComments();

        users.get(user).add(new CommentActivity(id, text));
    }

    public Collection<Content> getContent() {
        return new LinkedList<>(content);
    }

    @Override
    public Collection<Content> getNMostPopularContent(int n) {
        Validator.validateNonNegative(n, "n");

        return calcTopN(content, n, new ContentByPopularityComparator());
    }

    @Override
    public Collection<Content> getNMostRecentContent(String username, int n) {
        Validator.validateNotNull(username, "username");
        Validator.validateNonNegative(n, "n");

        User user = new User(username);
        if (!users.containsKey(user)) {
            throw new UsernameNotFoundException(
                "User with username : [" + username + "] doesn't exist!");
        }

        List<ContentHandler> userContent = new LinkedList<>();
        for (ContentHandler c : content) {
            if (c.getUser().username().equals(username)) {
                userContent.add(c);
            }
        }

        return calcTopN(userContent, n, new ContentByDateComparator());
    }

    private Collection<Content> calcTopN(Collection<ContentHandler> content, int n,
                                         Comparator<ContentHandler> comparator) {
        List<ContentHandler> validContent = new LinkedList<>();

        for (ContentHandler c : content) {
            if (!c.isExpired()) {
                validContent.add(c);
            }
        }

        validContent.sort(comparator);

        List<Content> mostRecentContent = new LinkedList<>();

        int limit = Math.min(n, validContent.size());
        for (int i = 0; i < limit; i++) {
            mostRecentContent.add(validContent.get(i));
        }

        return Collections.unmodifiableList(mostRecentContent);
    }

    @Override
    public String getMostPopularUser() {
        if (users.isEmpty()) {
            throw new NoUsersException("There are currently no users in the platform!");
        }

        User user = mentionsTracker.getMostPopularUser();

        return user == null ? "" : user.username();
    }

    @Override
    public Collection<Content> findContentByTag(String tag) {
        Validator.validateNotNull(tag, "tag");

        List<Content> contentByTag = new LinkedList<>();

        for (ContentHandler c : content) {
            if (!c.isExpired()) {
                Collection<String> tags = c.getTags();

                for (String t : tags) {
                    if (t.equalsIgnoreCase(tag)) {
                        contentByTag.add(c);
                    }
                }
            }
        }

        return Collections.unmodifiableCollection(contentByTag);
    }

    @Override
    public List<String> getActivityLog(String username) {
        Validator.validateNotNull(username, "username");

        User user = new User(username);
        if (!users.containsKey(user)) {
            throw new UsernameNotFoundException(
                "User with username : [" + username + "] doesn't exist!");
        }

        List<Activity> activities = this.getActivities(username);
        activities.sort(new ActivityByDateComparator());

        List<String> activityLog = new LinkedList<>();

        for (Activity activity : activities) {
            activityLog.add(activity.getFormattedLog());
        }

        return activityLog;
    }

    private List<Activity> getActivities(String username) {
        User user = new User(username);

        if (users.containsKey(user)) {
            return users.get(user);
        }

        return List.of();
    }

    private ContentHandler getContentById(String id) {
        for (ContentHandler c : content) {
            if (c.getId().equals(id)) {
                return c;
            }
        }

        throw new ContentNotFoundException("Content with ID: [" + id + "] is not found!");
    }

    private Collection<String> extractTags(String description) {
        return TagExtractor.extract(description);
    }

    private Collection<String> extractMentions(String description, String username) {
        Set<User> mentionedUsers = MentionExtractor.extract(description);
        mentionedUsers.retainAll(this.getUsers());

        User publishingUser = new User(username);

        if (!mentionedUsers.isEmpty()) {
            mentionsTracker.updateMentions(mentionedUsers, publishingUser);
        }

        List<String> mentions = new LinkedList<>();
        for (User user : mentionedUsers) {
            mentions.add(user.username());
        }

        return mentions;
    }

    private String generateId(String username) {
        return username + DASH + currentIdNumber++;
    }
}
