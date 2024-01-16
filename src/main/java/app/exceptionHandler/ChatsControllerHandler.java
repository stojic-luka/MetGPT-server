package app.exceptionHandler;

import app.exception.ChatIdNotExistException;
import app.exception.InputValidationException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ChatsControllerHandler {
    
    @ExceptionHandler(ChatIdNotExistException.class)
    public ResponseEntity<Map<String, Object>> handleChatIdMissingException(Exception e) {
        return ResponseEntity.badRequest().body(Map.ofEntries(
                Map.entry("message", "Chat ID provided does not exist"),
                Map.entry("success", false)
        ));
    }

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<Map<String, Object>> handleInputNullException(Exception e) {
        return ResponseEntity.badRequest().body(Map.ofEntries(
                Map.entry("message", "Input message cannot be null"),
                Map.entry("success", false)
        ));
    }
}
