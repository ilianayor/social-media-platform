package bg.sofia.uni.fmi.mjt.socialmedia.activity;

import java.time.LocalDateTime;

public abstract class InteractionActivity implements Activity {
    protected final LocalDateTime date;
    protected final String contentId;

    public InteractionActivity(String contentId) {
        this.date = LocalDateTime.now();
        this.contentId = contentId;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    public String getContentId() {
        return contentId;
    }
}
