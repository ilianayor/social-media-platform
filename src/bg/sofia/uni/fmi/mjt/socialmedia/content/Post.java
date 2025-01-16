package bg.sofia.uni.fmi.mjt.socialmedia.content;

import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;
import java.util.Collection;

public class Post extends SocialMediaContent {
    private static final long EXPIRATION_DAYS = 30;

    public Post(String id, LocalDateTime publishedOn, User user, Collection<String> tags,
                Collection<String> mentions) {
        super(id, publishedOn, user, tags, mentions);
    }

    @Override
    public boolean isExpired() {
        return this.getPublishedOn().plusDays(EXPIRATION_DAYS).isBefore(LocalDateTime.now());
    }

}
