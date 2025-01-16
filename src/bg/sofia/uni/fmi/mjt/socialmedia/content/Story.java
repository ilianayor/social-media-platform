package bg.sofia.uni.fmi.mjt.socialmedia.content;

import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;
import java.util.Collection;

public class Story extends SocialMediaContent {
    private static final long EXPIRATION_HOURS = 24;

    public Story(String id, LocalDateTime publishedOn, User user, Collection<String> tags,
                 Collection<String> mentions) {
        super(id, publishedOn, user, tags, mentions);
    }

    @Override
    public boolean isExpired() {
        return this.getPublishedOn().plusHours(EXPIRATION_HOURS).isBefore(LocalDateTime.now());
    }

}
