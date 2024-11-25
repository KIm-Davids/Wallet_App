package africa.semicolon.wollet.exception;

public class CustomerNotFoundException extends ResourceNotFoundException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
