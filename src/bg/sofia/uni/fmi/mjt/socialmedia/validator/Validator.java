package bg.sofia.uni.fmi.mjt.socialmedia.validator;

public class Validator {
    public static void validateNotNull(Object obj, String argName) {
        if (obj == null) {
            throw new IllegalArgumentException(argName + " cannot be null!");
        }
    }

    public static void validateNonNegative(Integer num, String argName) {
        if (num < 0) {
            throw new IllegalArgumentException(argName + " cannot be negative!");
        }
    }
}
