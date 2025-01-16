package bg.sofia.uni.fmi.mjt.socialmedia.comparator;

import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentHandler;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ContentByDateComparator implements Comparator<ContentHandler> {

    @Override
    public int compare(ContentHandler first, ContentHandler second) {
        LocalDateTime firstDate = first.getPublishedOn();
        LocalDateTime secondDate = second.getPublishedOn();

        return secondDate.compareTo(firstDate);
    }
}
