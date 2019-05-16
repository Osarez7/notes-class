package co.edu.intecap.notes.network.model;

import java.util.HashMap;
import java.util.List;

public class NotesResponse {
   HashMap<String, Note> notes;

    public HashMap<String, Note> getNotes() {
        return notes;
    }

    public void setNotes(HashMap<String, Note> notes) {
        this.notes = notes;
    }
}
