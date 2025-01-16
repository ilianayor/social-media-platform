package bg.sofia.uni.fmi.mjt.socialmedia.exceptions;

public class NoMentionsFoundException extends RuntimeException {
    public NoMentionsFoundException(String message) {
        super(message);
    }

    public NoMentionsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
