package co.edu.intecap.notes.view.presenters;

import android.util.Log;

import co.edu.intecap.notes.network.model.ApiUtils;
import co.edu.intecap.notes.network.model.Note;
import co.edu.intecap.notes.view.views.NoteFormView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteFormPresenter {

    NoteFormView noteFormView;

    public NoteFormPresenter(NoteFormView noteFormView) {
        this.noteFormView = noteFormView;
    }

    public void createNote(Note note) {
        note.setId("jejeje");
        ApiUtils.getAPIService().saveNote(note).enqueue(new Callback<co.edu.intecap.notes.network.model.Response>() {
            @Override
            public void onResponse(Call<co.edu.intecap.notes.network.model.Response> call, Response<co.edu.intecap.notes.network.model.Response> response) {
                noteFormView.onNoteCreateSuccess();
            }

            @Override
            public void onFailure(Call<co.edu.intecap.notes.network.model.Response> call, Throwable t) {
                noteFormView.onNoteUpdateError(t.getMessage());
            }
        });
    }

    public void getNote(String noteId) {
        ApiUtils.getAPIService().getNote(noteId).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                noteFormView.onGetNoteSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                noteFormView.onGetNoteError(t.getMessage());
            }
        });
    }


    public void updateNote(String nodeId, Note note) {
        ApiUtils.getAPIService().updateNote(nodeId, note).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                noteFormView.onNoteUpdateSuccess();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}

