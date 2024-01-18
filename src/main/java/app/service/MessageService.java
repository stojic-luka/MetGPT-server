package app.service;

import app.exception.ChatIdNotExistException;
import app.model.Message;
import app.repository.MessagesRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessagesRepository messageRepository;

    @Autowired
    public MessageService(MessagesRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Retrieves a list of messages for the given chat ID.
     *
     * @param  chatId  The ID of the chat to retrieve messages for.
     * @return         The list of messages, sorted by creation date.
     */
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

    /**
     * Adds a message to the specified chat.
     *
     * @param  chatId   the ID of the chat
     * @param  content  the content of the message
     */
    public void addMessage(String chatId, String content) {
        this.addMessage(chatId, content, false);
    }

    /**
     * Adds a message to the message repository.
     *
     * @param  chatId   the ID of the chat
     * @param  content  the content of the message
     * @param  isBot    true if the message is from a bot, false otherwise
     * @throws ChatIdNotExistException if the chat ID is not a valid UUID
     */
    public void addMessage(String chatId, String content, boolean isBot) {
        try {
            messageRepository.addMessage(UUID.fromString(chatId), content, isBot);
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }
}
