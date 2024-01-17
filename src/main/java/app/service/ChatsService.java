package app.service;

import app.exception.ChatIdNotExistException;
import app.model.Chats;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.ChatsRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

@Service
public class ChatsService {

    private final ChatsRepository chatRepository;

    @Autowired
    public ChatsService(ChatsRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chats> getChats() {
        List<Chats> chatList = chatRepository.findAll();
        Comparator<Chats> comparator = (chat1, chat2) -> chat2.getCreatedAt().compareTo(chat1.getCreatedAt());
        Collections.sort(chatList, comparator);
        return chatList;
    }

    public UUID addChat(String title) {
        UUID randUUID = UUID.randomUUID();
        chatRepository.addChat(randUUID, title);
        return randUUID;
    }

    public void updateChatTitle(String chatId, String title) {
        try {
            chatRepository.updateChatTitle(UUID.fromString(chatId), title);
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }

    public void deleteChat(String chatId) {
        try {
            chatRepository.deleteMessagesInChat(UUID.fromString(chatId));
            chatRepository.deleteChat(UUID.fromString(chatId));
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }

    public boolean chatExists(String chatId) {
        try {
            return chatRepository.chatExists(UUID.fromString(chatId)) == 1;
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }
}
