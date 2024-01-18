package app.controller;

import app.service.NotesService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/")
public class NotesController {
    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    /**
     * Retrieves the notes from the API.
     *
     * @return         	The ResponseEntity object containing the notes and success status.
     */
    @GetMapping(
        value = "/notes",
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>>
    getNotes() {
        return ResponseEntity.ok().body(Map.ofEntries(
            Map.entry("notes", notesService.getNotes()),
            Map.entry("success", true)));
    }
}
