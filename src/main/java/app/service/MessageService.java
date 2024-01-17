package app.service;

import app.exception.ChatIdNotExistException;
import app.model.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.MessagesRepository;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MessageService {

    private final MessagesRepository messageRepository;

    @Autowired
    public MessageService(MessagesRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages(String chatId) {
        try {
            List<Message> messageList = messageRepository.findAllMessagesByChat(UUID.fromString(chatId));
            Comparator<Message> comparator = (chat1, chat2) -> chat1.getCreatedAt().compareTo(chat2.getCreatedAt());
            Collections.sort(messageList, comparator);
            return messageList;
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }

    public void addMessage(String chatId, String content) {
        this.addMessage(chatId, content, false);
    }

    public void addMessage(String chatId, String content, boolean isBot) {
        try {
            messageRepository.addMessage(UUID.fromString(chatId), content, isBot);
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }
}
