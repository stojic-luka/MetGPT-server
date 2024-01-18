package app.service;

import app.exception.ChatIdNotExistException;
import app.model.Chats;
import app.repository.ChatsRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatsService {
    private final ChatsRepository chatRepository;

    @Autowired
    public ChatsService(ChatsRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * Retrieves a list of Chats from the chat repository, sorts them based on the created date,
     * and returns the sorted list.
     *
     * @return  a list of Chats sorted by the created date
     */
    public List<Chats> getChats() {
        List<Chats> chatList = chatRepository.findAll();
        Comparator<Chats> comparator = (chat1, chat2) -> chat1.getCreatedAt().compareTo(chat2.getCreatedAt());
        Collections.sort(chatList, comparator);
        return chatList;
    }

    /**
     * Adds a chat with the given title.
     *
     * @param  title  the title of the chat
     * @return        the UUID of the added chat
     */
    public UUID addChat(String title) {
        UUID randUUID = UUID.randomUUID();
        chatRepository.addChat(randUUID, title);
        return randUUID;
    }

    /**
     * Updates the chat title.
     *
     * @param  chatId  the ID of the chat to update
     * @param  title   the new title for the chat
     */
    public void updateChatTitle(String chatId, String title) {
        try {
            chatRepository.updateChatTitle(UUID.fromString(chatId), title);
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }

    /**
     * Deletes a chat and all its messages based on the provided chat ID.
     *
     * @param  chatId  the ID of the chat to be deleted
     */
    public void deleteChat(String chatId) {
        try {
            chatRepository.deleteMessagesInChat(UUID.fromString(chatId));
            chatRepository.deleteChat(UUID.fromString(chatId));
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }

    /**
     * Checks if a chat exists based on the given chat ID.
     *
     * @param  chatId the ID of the chat to check
     * @return true if the chat exists, false otherwise
     */
    public boolean chatExists(String chatId) {
        try {
            return chatRepository.chatExists(UUID.fromString(chatId)) == 1;
        } catch (NumberFormatException e) {
            throw new ChatIdNotExistException();
        }
    }
}
