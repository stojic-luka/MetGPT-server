package app.service;

import app.model.Note;
import app.repository.NotesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {
    private final NotesRepository notesRepository;

    @Autowired
    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    /**
     * Retrieves a list of notes.
     *
     * @return  a list of note objects
     */
    public List<Note> getNotes() {
        return notesRepository.findAll();
    }
}
