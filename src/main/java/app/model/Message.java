package app.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table
public class Message {

    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "message_sequence"
    )
    private long messageId;
    
    @Nonnull
    private UUID chatId;
    
    @Nonnull
    private String content = "";
    
    @Nonnull
    @Column(columnDefinition = "bit(1) default 0")
    private boolean senderBot = false;

    @Nonnull
    @CreationTimestamp
    @Column(columnDefinition = "DATETIME default current_timestamp(6)")
    private final LocalDateTime createdAt;

    public Message() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Message(UUID chatId, String content, boolean senderBot) {
        this.chatId = chatId;
        this.content = content;
        this.senderBot = senderBot;
        this.createdAt = LocalDateTime.now();
    }

    public Message(String chatId, String content, boolean senderBot) {
        this.chatId = UUID.fromString(chatId);
        this.content = content;
        this.senderBot = senderBot;
        this.createdAt = LocalDateTime.now();
    }

    public Message(long messageId, UUID chatId, String content, boolean senderBot) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.content = content;
        this.senderBot = senderBot;
        this.createdAt = LocalDateTime.now();
    }

    public Message(long messageId, String chatId, String content, boolean senderBot) {
        this.messageId = messageId;
        this.chatId = UUID.fromString(chatId);
        this.content = content;
        this.senderBot = senderBot;
        this.createdAt = LocalDateTime.now();
    }

    public long getMessageId() {
        return messageId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public String getContent() {
        return content;
    }

    public boolean isSenderBot() {
        return senderBot;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Message(\n");
        sb.append(String.format("\tid: %d,\n", getMessageId()));
        sb.append(String.format("\tcontent:\n \t\t%s\n", getContent()));
        sb.append(String.format("\tisSenderBot: %s,\n", isSenderBot() ? "true" : "false"));
        sb.append(String.format("\tcreatedAt: %s\n)", getCreatedAt().toString()));
        return sb.toString();
    }
}
