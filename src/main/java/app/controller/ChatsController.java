package app.controller;

import app.model.Chats;
import app.service.ChatsService;
import java.util.List;
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
public class ChatsController {

    private final ChatsService chatsService;

    @Autowired
    public ChatsController(ChatsService chatsService) {
        this.chatsService = chatsService;
    }

    @GetMapping(
            value = "/chats",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, Object>> getChats() {
        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("success", true),
                Map.entry("chats", chatsService.getChats())
        ));
    }

    @PostMapping(
            value = "/chats",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, Object>> addChat(@RequestBody Chats chat) {
        UUID chatId = chatsService.addChat(chat.getTitle());
        
        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("success", true),
                Map.entry("chatId", chatId.toString())
        ));
    }

    @PutMapping(
            value = "/chats/{chatId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, Object>> updateChatTitle(@PathVariable(value = "chatId") String chatId, @RequestBody Chats chat) {
        if (!chatsService.chatExists(chatId)) {
            return ResponseEntity.badRequest().body(Map.ofEntries(
                    Map.entry("success", false),
                    Map.entry("message", "Chat ID provided does not exit in currently active chats")
            ));
        }
        
        chatsService.updateChatTitle(chatId, chat.getTitle());
        
        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("success", true)
        ));
    }

    @DeleteMapping(
            value = "/chats/{chatId}"
    )
    public ResponseEntity<Map<String, Object>> deleteChat(@PathVariable(value = "chatId") String chatId) {
        if (!chatsService.chatExists(chatId)) {
            return ResponseEntity.badRequest().body(Map.ofEntries(
                    Map.entry("success", false),
                    Map.entry("message", "Chat ID provided does not exit in currently active chats")
            ));
        }
        
        chatsService.deleteChat(chatId);
        
        return ResponseEntity.ok().body(Map.ofEntries(
                Map.entry("success", true)
        ));
    }
}
