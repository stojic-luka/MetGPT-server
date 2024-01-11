package app.repository;

import app.model.Chats;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, UUID> {

    @Transactional
    @Modifying
    @Query("INSERT INTO Chats c (c.chatId, c.title) VALUES (:chatId, :title)")
    void addChat(@Param("chatId") UUID chatId, @Param("title") String title);

    @Transactional
    @Modifying
    @Query("UPDATE Chats c SET c.title = :title WHERE c.chatId = :chatId")
    void updateChatTitle(@Param("chatId") UUID chatId, @Param("title") String title);

    @Transactional
    @Modifying
    @Query("DELETE FROM Chats c WHERE c.chatId = :chatId")
    void deleteChat(@Param("chatId") UUID chatId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Message m WHERE m.chatId = :chatId")
    void deleteMessagesInChat(@Param("chatId") UUID chatId);
    
    @Query("SELECT COUNT(*) FROM Chats c WHERE c.chatId = :chatId")
    boolean chatExists(@Param("chatId") UUID chatId);
}
