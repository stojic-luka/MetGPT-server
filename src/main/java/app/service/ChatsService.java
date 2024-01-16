package app.service;

import app.model.Chats;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.ChatsRepository;
import java.util.UUID;

@Service
public class ChatsService {
    
    private final ChatsRepository chatRepository;
    
    @Autowired
    public ChatsService(ChatsRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
    
    public List<Chats> getChats() {
        return chatRepository.findAll();
    }
    
    public UUID addChat(String title) {
        UUID randUUID = UUID.randomUUID();
        chatRepository.addChat(randUUID, title);
        return randUUID;
    }
    
    public void updateChatTitle(String chatId, String title) {
        chatRepository.updateChatTitle(UUID.fromString(chatId), title);
    }
    
    public void deleteChat(String chatId) {
        chatRepository.deleteMessagesInChat(UUID.fromString(chatId));
        chatRepository.deleteChat(UUID.fromString(chatId));
    }
    
    public boolean chatExists(String chatId) {
        return chatRepository.chatExists(UUID.fromString(chatId)) == 1;
    }
}
