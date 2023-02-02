package code.challenge.filehandler.excetption;

public class BadRequestException extends ServerException {
    public BadRequestException(String message) {
        super(message);
    }
}
