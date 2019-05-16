package co.edu.intecap.notes.view.views;

import co.edu.intecap.notes.network.model.Note;

public interface NoteFormView {
    public void onNoteCreateSuccess();
    public void onNoteCreateError(String error);

    public void onNoteUpdateSuccess();
    public void onNoteUpdateError(String error);

    public void onGetNoteSuccess(Note note);
    public void onGetNoteError(String error);
}
