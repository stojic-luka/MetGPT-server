package app.controller;

import app.model.Message;
import app.model.bodies.AiRequestBody;
import app.service.AiService;
import app.service.ChatsService;
import app.service.MessageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/")
public class MessageController {

    private final ChatsService chatsService;
    private final MessageService messageService;
    private final AiService aiService;

    @Autowired
    public MessageController(ChatsService chatsService, MessageService messageService, AiService aiService) {
        this.chatsService = chatsService;
        this.messageService = messageService;
        this.aiService = aiService;
    }

    @PostMapping(
            value = "/chats/{chatId}/chat",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, Object>> chat(@PathVariable(value = "chatId") String chatId, @RequestBody AiRequestBody req) {
//        if (!chatsService.chatExists(chatId)) {
//            return ResponseEntity.badRequest().body(Map.ofEntries(
//                    Map.entry("success", false),
//                    Map.entry("message", "Chat ID provided does not exit in currently active chats")
//            ));
//        }
        if (req.getInput() == null) {
            return ResponseEntity.badRequest().body(Map.ofEntries(
                    Map.entry("success", false),
                    Map.entry("message", "Input message cannot be null")
            ));
        }

        messageService.addMessage(chatId, req.getInput(), false);
        String resp = aiService.getResponse(req.toJsonString());
        messageService.addMessage(chatId, resp, true);

        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("success", true),
                Map.entry("response", resp)
        ));
    }

    @GetMapping(
            value = "/chats/{chatId}/messages",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, Object>> getMessages(@PathVariable(value = "chatId") String chatId) {
        if (!chatsService.chatExists(chatId)) {
            return ResponseEntity.badRequest().body(Map.ofEntries(
                    Map.entry("success", false),
                    Map.entry("message", "Chat ID provided does not exit in currently active chats")
            ));
        }

        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("success", true),
                Map.entry("messages", messageService.getMessages(chatId))
        ));
    }

    @PostMapping(
            value = "/chats/{chatId}/messages",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, Object>> addMessage(@PathVariable(value = "chatId") String chatId, @RequestBody Message message) {
        if (!chatsService.chatExists(chatId)) {
            return ResponseEntity.badRequest().body(Map.ofEntries(
                    Map.entry("success", false),
                    Map.entry("message", "Chat ID provided does not exit in currently active chats")
            ));
        }

        if (message.getContent() == null) {
            return ResponseEntity.badRequest().body(Map.ofEntries(
                    Map.entry("success", false),
                    Map.entry("message", "Input message cannot be null")
            ));
        }

        messageService.addMessage(chatId, message.getContent(), message.isSenderBot());

        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("success", true)
        ));
    }
}
