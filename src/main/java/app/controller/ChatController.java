package app.controller;

import app.exception.ChatIdNotExistException;
import app.exception.InputValidationException;
import app.model.Chats;
import app.model.Message;
import app.service.AiService;
import app.service.ChatsService;
import app.service.MessageService;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/")
public class ChatController {
    private final ChatsService chatsService;
    private final MessageService messageService;
    private final AiService aiService;

    @Autowired
    public ChatController(ChatsService chatsService, MessageService messageService, AiService aiService) {
        this.chatsService = chatsService;
        this.messageService = messageService;
        this.aiService = aiService;
    }

    /**
     * Retrieves a list of chats.
     *
     * @return         	A ResponseEntity containing a Map with the "chats" key
     *                  mapped to the list of chats, and the "success" key mapped
     *                  to a boolean value indicating the success of the operation.
     */
    @GetMapping(
        value = "/chats",
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    getChats() {
        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("chats", chatsService.getChats()),
            Map.entry("success", true)));
    }

    /**
     * Adds a chat to the database.
     *
     * @param  chat  the chat object to be added
     * @return       a ResponseEntity containing a map with the chatId and success status
     */
    @PostMapping(
        value = "/chats",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    addChat(@RequestBody Chats chat) {
        if (chat.getTitle().isBlank()) {
            throw new InputValidationException();
        }

        UUID chatId = chatsService.addChat(chat.getTitle());

        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("chatId", chatId.toString()),
            Map.entry("success", true)));
    }

    /**
     * Updates the title of a chat.
     *
     * @param  chatId  the ID of the chat to update
     * @param  chat    the updated chat object
     * @return         the response entity with a map containing the success status
     */
    @PutMapping(
        value = "/chats/{chatId}",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    updateChatTitle(@PathVariable(value = "chatId") String chatId, @RequestBody Chats chat) {
        if (!chatsService.chatExists(chatId)) {
            throw new ChatIdNotExistException();
        }

        chatsService.updateChatTitle(chatId, chat.getTitle());

        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("success", true)));
    }

    /**
     * Deletes a chat with the given chatId.
     *
     * @param  chatId  the ID of the chat to delete
     * @return         a ResponseEntity containing a map with the key "success" and the value true if the chat was successfully deleted
     */
    @DeleteMapping(
        value = "/chats/{chatId}",
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    deleteChat(@PathVariable(value = "chatId") String chatId) {
        if (!chatsService.chatExists(chatId)) {
            throw new ChatIdNotExistException();
        }

        chatsService.deleteChat(chatId);

        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("success", true)));
    }

    /**
     * Adds users message to the database and after contacting AI service
     * it adds bot message to database and returns it to user.
     *
     * @param  chatId    the ID of the chat
     * @param  req       the message request
     * @return           a ResponseEntity containing a map with the response and success flag
     */
    @PostMapping(
        value = "/chats/{chatId}/chat",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    chat(@PathVariable(value = "chatId") String chatId, @RequestBody Message req) {
        if (!chatsService.chatExists(chatId)) {
            throw new ChatIdNotExistException();
        }
        if (req.getContent().isBlank()) {
            throw new InputValidationException();
        }

        messageService.addMessage(chatId, req.getContent());
        String resp = aiService.getResponse(req.getContent());
        messageService.addMessage(chatId, resp, true);

        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("response", resp),
            Map.entry("success", true)));
    }

    /**
     * Retrieves the messages for a given chat ID.
     *
     * @param  chatId  the ID of the chat
     * @return         a ResponseEntity containing a map of messages and a success flag
     */
    @GetMapping(
        value = "/chats/{chatId}/messages",
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    getMessages(@PathVariable(value = "chatId") String chatId) {
        if (!chatsService.chatExists(chatId)) {
            throw new ChatIdNotExistException();
        }

        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("messages", messageService.getMessages(chatId)),
            Map.entry("success", true)));
    }

    /**
     * Adds a new message to a chat.
     *
     * @param  chatId   the ID of the chat
     * @param  message  the message to be added
     * @return          the response entity containing a success indicator
     */
    @PostMapping(
        value = "/chats/{chatId}/messages",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    addMessage(@PathVariable(value = "chatId") String chatId, @RequestBody Message message) {
        if (!chatsService.chatExists(chatId)) {
            throw new ChatIdNotExistException();
        }
        if (message.getContent().isBlank()) {
            throw new InputValidationException();
        }

        messageService.addMessage(chatId, message.getContent(), message.isSenderBot());

        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("success", true)));
    }
}
