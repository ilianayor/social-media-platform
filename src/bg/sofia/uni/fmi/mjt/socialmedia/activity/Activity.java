package bg.sofia.uni.fmi.mjt.socialmedia.activity;

import java.time.LocalDateTime;

public interface Activity {
    String DATE_FORMAT = "HH:mm:ss dd.MM.yy";

    LocalDateTime getDate();

    String getFormattedLog();
}
