package co.edu.intecap.notes.view.views;

import java.util.List;

import co.edu.intecap.notes.network.model.Note;
import co.edu.intecap.notes.network.model.NotesResponse;

public interface MainView {
    public void onGetAllNotes(List<Note> notes);
    public void onGetAllNotes(NotesResponse notes);
    public void onGetAllNotesError(String error);
}
