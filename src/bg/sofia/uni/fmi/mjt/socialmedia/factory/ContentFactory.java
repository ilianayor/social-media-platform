package bg.sofia.uni.fmi.mjt.socialmedia.factory;

import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentHandler;
import bg.sofia.uni.fmi.mjt.socialmedia.enums.ContentType;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ContentFactory {
    ContentHandler createContent(String id, LocalDateTime publishedOn, User user,
                                 Collection<String> tags, Collection<String> mentions, ContentType type);
}

