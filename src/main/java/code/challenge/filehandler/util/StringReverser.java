package code.challenge.filehandler.util;

public class StringReverser {
    private StringReverser() {
    }

    public static String reverse(String content) {
        if (content == null) return null;

        return new StringBuilder()
                .append(content)
                .reverse()
                .toString();
    }
}
