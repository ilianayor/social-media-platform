package bg.sofia.uni.fmi.mjt.socialmedia.comparator;

import bg.sofia.uni.fmi.mjt.socialmedia.activity.Activity;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ActivityByDateComparator implements Comparator<Activity> {

    @Override
    public int compare(Activity first, Activity second) {
        LocalDateTime firstDate = first.getDate();
        LocalDateTime secondDate = second.getDate();

        return secondDate.compareTo(firstDate);
    }
}
