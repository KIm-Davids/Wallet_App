package africa.semicolon.wollet.exception;

public class InvalidDetailException extends ResourceNotFoundException {
    public InvalidDetailException(String invalidDetails) {
        super(invalidDetails);
    }
}
