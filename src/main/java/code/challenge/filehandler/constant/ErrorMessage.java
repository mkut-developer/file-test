package code.challenge.filehandler.constant;

public interface ErrorMessage {
    String ERROR = "error";
    String BAD_CONTENT = "failed to read the content of the file.";
    String NOT_TEXT_CONTENT = "content should be text.";
    String WRONG_LINE = "failed to convert content to model (check all fields).";
    String DB_ERROR = "db error.";
    String TYPE_ERROR = "inappropriate content type.";
    String INTERNAL_ERROR = "internal server error.";

}
