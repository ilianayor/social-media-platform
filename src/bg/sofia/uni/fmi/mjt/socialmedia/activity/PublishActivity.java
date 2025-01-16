package bg.sofia.uni.fmi.mjt.socialmedia.activity;

import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentHandler;

import java.time.LocalDateTime;

public abstract class PublishActivity implements Activity {
    protected final LocalDateTime date;
    protected final ContentHandler content;

    public PublishActivity(LocalDateTime date, ContentHandler content) {
        this.date = date;
        this.content = content;
    }

    public ContentHandler getContent() {
        return content;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

}
