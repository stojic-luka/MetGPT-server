package app.exception;

public class ChatIdNotExistException extends RuntimeException {

    public ChatIdNotExistException() {
        super("Chat does not exist");
    }
}
