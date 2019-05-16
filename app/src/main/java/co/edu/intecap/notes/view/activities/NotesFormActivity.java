package co.edu.intecap.notes.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.model.NoteEntity;
import co.edu.intecap.notes.model.database.NotesDatabase;
import co.edu.intecap.notes.network.model.Note;
import co.edu.intecap.notes.view.presenters.NoteFormPresenter;
import co.edu.intecap.notes.view.views.NoteFormView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class NotesFormActivity extends AppCompatActivity implements NoteFormView {

    public static final String EXTRA_NOTE_ID = "noteId";
    private Button btnSave;
    private TextInputLayout inputName;
    private TextInputLayout inputContent;
    private Switch swFavorite;
    private String noteId = null;
    private NoteFormPresenter presenter;

    public static Intent newIntent(Context context, String noteId) {
        Intent intent = new Intent(context, NotesFormActivity.class);
        intent.putExtra(NotesFormActivity.EXTRA_NOTE_ID, noteId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_form);
        Intent intent = getIntent();
        if (intent != null) {
            noteId = intent.getStringExtra(EXTRA_NOTE_ID);
        }


        btnSave = findViewById(R.id.btn_save);
        presenter = new NoteFormPresenter(this);

        inputName = findViewById(R.id.input_name);
        inputContent = findViewById(R.id.input_content);
        swFavorite = findViewById(R.id.sw_favorite);

        setupNote();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteId == null) {
                    Note note = new Note();
                    note.setName(inputName.getEditText().getText().toString());
                    note.setContent(inputContent.getEditText().getText().toString());
                    note.setFavorite(swFavorite.isChecked());
                    note.setCreatedDate(new Date());
                    presenter.createNote(note);
                } else {
                    presenter.getNote(noteId);
                }


            }
        });


    }

    private void setupNote() {
        if (noteId != null) {
//          NoteEntity note =  presenter.getNote(noteId);
//          inputName.getEditText().setText(note.getName());
//          inputContent.getEditText().setText(note.getContent());
//          swFavorite.setChecked(note.isFavorite());
        }
    }

    @Override
    public void onNoteCreateSuccess() {
        finish();
    }

    @Override
    public void onNoteCreateError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNoteUpdateSuccess() {
        finish();
    }

    @Override
    public void onNoteUpdateError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetNoteSuccess(Note note) {
        note.setName(inputName.getEditText().getText().toString());
        note.setContent(inputContent.getEditText().getText().toString());
        note.setFavorite(swFavorite.isChecked());
//        presenter.updateNote(note);
    }

    @Override
    public void onGetNoteError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


}
