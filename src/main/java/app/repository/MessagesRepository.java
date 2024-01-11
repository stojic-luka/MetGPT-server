package app.repository;

import app.model.Message;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.chatId = :chatId")
    List<Message> findAllMessagesByChat(@Param("chatId") UUID chatId);

    @Transactional
    @Modifying
    @Query("INSERT INTO Message m (m.chatId, m.content, m.senderBot) VALUES (:chatId, :content, :isBot)")
    void addMessage(@Param("chatId") UUID chatId, @Param("content") String content, @Param("isBot") boolean isBot);
}
