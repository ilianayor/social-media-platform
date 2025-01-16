package bg.sofia.uni.fmi.mjt.socialmedia.activity;

import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StoryActivity extends PublishActivity {

    public StoryActivity(LocalDateTime date, ContentHandler content) {
        super(date, content);
    }

    @Override
    public String getFormattedLog() {
        String formattedDate = this.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        return String.format("%s: Created a story with id [%s]", formattedDate, content.getId());
    }
}
