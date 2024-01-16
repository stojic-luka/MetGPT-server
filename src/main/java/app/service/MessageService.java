package app.service;

import app.model.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.MessagesRepository;

@Service
public class MessageService {
    
    private final MessagesRepository messageRepository;
    
    @Autowired
    public MessageService(MessagesRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    
    public List<Message> getMessages(String chatId) {
        return messageRepository.findAllMessagesByChat(UUID.fromString(chatId));
    }
    
    public void addMessage(String chatId, String content) {
        messageRepository.addMessage(UUID.fromString(chatId), content, false);
    }
    
    public void addMessage(String chatId, String content, boolean isBot) {
        messageRepository.addMessage(UUID.fromString(chatId), content, isBot);
    }
}
