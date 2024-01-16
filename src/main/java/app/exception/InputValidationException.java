package app.exception;

public class InputValidationException extends RuntimeException {

    public InputValidationException() {
        super("Input message cannot be null");
    }
}
