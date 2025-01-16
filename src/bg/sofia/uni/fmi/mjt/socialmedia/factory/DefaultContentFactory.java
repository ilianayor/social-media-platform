package bg.sofia.uni.fmi.mjt.socialmedia.factory;

import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentHandler;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Story;
import bg.sofia.uni.fmi.mjt.socialmedia.enums.ContentType;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;
import java.util.Collection;

public class DefaultContentFactory implements ContentFactory {
    @Override
    public ContentHandler createContent(String id, LocalDateTime publishedOn, User user,
                                        Collection<String> tags, Collection<String> mentions, ContentType type) {
        return switch (type) {
            case STORY -> new Story(id, publishedOn, user, tags, mentions);
            case POST -> new Post(id, publishedOn, user, tags, mentions);
        };
    }
}
