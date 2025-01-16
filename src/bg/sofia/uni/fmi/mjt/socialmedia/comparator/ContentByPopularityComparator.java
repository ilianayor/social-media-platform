package bg.sofia.uni.fmi.mjt.socialmedia.comparator;

import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentHandler;

import java.util.Comparator;

public class ContentByPopularityComparator implements Comparator<ContentHandler> {

    @Override
    public int compare(ContentHandler first, ContentHandler second) {
        int firstTotal = first.totalNumberOfLikesAndComments();
        int secondTotal = second.totalNumberOfLikesAndComments();

        return Integer.compare(secondTotal, firstTotal);
    }
}
