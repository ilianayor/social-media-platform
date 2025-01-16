package bg.sofia.uni.fmi.mjt.socialmedia.content;

import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;

public interface ContentHandler extends Content {
    void increaseLikes();

    void increaseComments();

    int totalNumberOfLikesAndComments();

    LocalDateTime getPublishedOn();

    User getUser();

    boolean isExpired();
}
