package code.challenge.filehandler.util;

import code.challenge.filehandler.constant.ErrorMessage;
import code.challenge.filehandler.entity.Document;
import code.challenge.filehandler.entity.Line;
import code.challenge.filehandler.excetption.BadArgumentException;
import org.springframework.http.MediaType;

import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ResponseBuilder {

    private ResponseBuilder() {
    }


    public static Object buildResponseOfLineByMediaType(MediaType mediaType, Line line, Document document) {
        if (MediaType.TEXT_PLAIN.equals(mediaType)) {
            return line.getContent();
        } else if (MediaType.APPLICATION_JSON.equals(mediaType)) {
            return Map.of(
                    "content", line.getContent(),
                    "fileName", document.getName(),
                    "lineNumber", line.getIndex(),
                    "letter", maximumOccuringChar(line.getContent())
            );
        } else if (MediaType.APPLICATION_XML.equals(mediaType)) {
            return String.format("""
                    <line>
                       <content>%s</content>
                       <fileName>%s</fileName>
                       <lineNumber>%s</lineNumber>
                       <letter>%s</letter>
                    </line>""", line.getContent(), document.getName(), line.getIndex(), maximumOccuringChar(line.getContent()));
        } else throw new BadArgumentException(String.format("%s [{%s}]", ErrorMessage.TYPE_ERROR, mediaType.getType()));
    }

    public static String maximumOccuringChar(String str) {
        if (str.isEmpty()) return "(line is empty)";
        return str.chars()
                .mapToObj(x -> (char) x)
                .collect(groupingBy(x -> x, counting()))
                .entrySet().stream()
                .max(comparingByValue())
                .get()
                .getKey().toString();
    }
}
