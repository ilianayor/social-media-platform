package bg.sofia.uni.fmi.mjt.socialmedia.activity;

import java.time.format.DateTimeFormatter;

public class CommentActivity extends InteractionActivity {
    private final String text;

    public CommentActivity(String contentId, String text) {
        super(contentId);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getFormattedLog() {
        String formattedDate = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        return String.format("%s: Commented \"%s\" on a content with id [%s]", formattedDate, text, contentId);
    }

}
