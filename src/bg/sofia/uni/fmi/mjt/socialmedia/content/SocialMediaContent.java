package bg.sofia.uni.fmi.mjt.socialmedia.content;

import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;
import java.util.Collection;

public abstract class SocialMediaContent implements ContentHandler {
    protected final String id;
    protected int numberOfLikes;
    protected int numberOfComments;
    protected Collection<String> tags;
    protected Collection<String> mentions;
    protected final LocalDateTime publishedOn;
    protected final User user;

    public SocialMediaContent(String id, LocalDateTime publishedOn, User user, Collection<String> tags,
                              Collection<String> mentions) {
        this.id = id;
        this.numberOfLikes = 0;
        this.numberOfComments = 0;
        this.tags = tags;
        this.mentions = mentions;
        this.publishedOn = publishedOn;
        this.user = user;
    }

    @Override
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    @Override
    public int getNumberOfComments() {
        return numberOfComments;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<String> getTags() {
        return tags;
    }

    @Override
    public Collection<String> getMentions() {
        return mentions;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    @Override
    public void increaseLikes() {
        numberOfLikes++;
    }

    @Override
    public void increaseComments() {
        numberOfComments++;
    }

    @Override
    public String toString() {
        return "SocialMediaContent{" +
            "id='" + id + '\'' +
            ", numberOfLikes=" + numberOfLikes +
            ", numberOfComments=" + numberOfComments +
            ", tags=" + tags +
            ", mentions=" + mentions +
            ", publishedOn=" + publishedOn +
            '}';
    }

    @Override
    public int totalNumberOfLikesAndComments() {
        return numberOfComments + numberOfLikes;
    }

    @Override
    public User getUser() {
        return user;
    }
}
