package bg.sofia.uni.fmi.mjt.socialmedia.comparator;

import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class MentionsComparator implements Comparator<Map.Entry<User, Set<User>>> {

    @Override
    public int compare(Map.Entry<User, Set<User>> entry1, Map.Entry<User, Set<User>> entry2) {
        int size1 = entry1.getValue().size();
        int size2 = entry2.getValue().size();

        return Integer.compare(size2, size1);
    }
}
