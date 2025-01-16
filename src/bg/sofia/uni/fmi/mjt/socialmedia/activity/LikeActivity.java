package bg.sofia.uni.fmi.mjt.socialmedia.activity;

import java.time.format.DateTimeFormatter;

public class LikeActivity extends InteractionActivity {

    public LikeActivity(String contentId) {
        super(contentId);
    }

    @Override
    public String getFormattedLog() {
        String formattedDate = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        return String.format("%s: Liked a content with id [%s]", formattedDate, contentId);
    }

}
