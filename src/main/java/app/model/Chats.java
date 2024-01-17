package app.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table
public class Chats {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID chatId;
    
    @Nonnull
    private String title = "";

    @Nonnull
    @CreationTimestamp
    @Column(columnDefinition = "DATETIME default current_timestamp(6)")
    private final LocalDateTime createdAt;

    public Chats() {
        this.createdAt = LocalDateTime.now();
    }

    public Chats(String chatId, String title) {
        this.chatId = UUID.fromString(chatId);
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public Chats(UUID chatId, String title) {
        this.chatId = chatId;
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getChatId() {
        return chatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chat(\n");
        sb.append(String.format("\tid: %S,\n", getChatId().toString()));
        sb.append(String.format("\ttitle: %s\n", getTitle()));
        sb.append(String.format("\tcreatedAt: %s\n)", getCreatedAt().toString()));
        return sb.toString();
    }
}
