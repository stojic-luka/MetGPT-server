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
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID noteId;

    @Nonnull
    private String noteTitle;

    @Nonnull
    private String content;

    @Nonnull
    @CreationTimestamp
    @Column(columnDefinition = "DATETIME default current_timestamp(6)")
    private final LocalDateTime createdAt;

    public Note() {
        createdAt = LocalDateTime.now();
    }

    public Note(UUID noteId, String noteTitle) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.content = "";
        createdAt = LocalDateTime.now();
    }

    public Note(String noteId, String noteTitle) {
        this.noteId = UUID.fromString(noteId);
        this.noteTitle = noteTitle;
        this.content = "";
        createdAt = LocalDateTime.now();
    }

    public Note(UUID noteId, String noteTitle, String content) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.content = content;
        createdAt = LocalDateTime.now();
    }

    public Note(String noteId, String noteTitle, String content) {
        this.noteId = UUID.fromString(noteId);
        this.noteTitle = noteTitle;
        this.content = content;
        createdAt = LocalDateTime.now();
    }

    public UUID getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Note{");
        sb.append("noteId=").append(noteId);
        sb.append(", noteTitle=").append(noteTitle);
        sb.append(", content=").append(content);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
