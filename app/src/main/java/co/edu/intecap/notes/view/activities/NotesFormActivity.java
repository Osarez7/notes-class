package co.edu.intecap.notes.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.model.Note;
import co.edu.intecap.notes.model.database.NotesDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class NotesFormActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "noteId";
    private Button btnSave;
    private TextInputLayout inputName;
    private TextInputLayout inputContent;
    private Switch swFavorite;
    private int EMPTY_NOTE = -1;
    private long noteId = EMPTY_NOTE;

    private NotesDatabase notesDatabase;
    public static Intent newIntent(Context context, long noteId) {
        Intent intent = new Intent(context, NotesFormActivity.class);
        intent.putExtra(NotesFormActivity.EXTRA_NOTE_ID, noteId);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_form);
        Intent intent =  getIntent();
        if( intent != null ){
            noteId =  intent.getLongExtra(EXTRA_NOTE_ID, EMPTY_NOTE);
        }

        notesDatabase = NotesDatabase.getInstance(this);

        btnSave = findViewById(R.id.btn_save);

        inputName = findViewById(R.id.input_name);
        inputContent = findViewById(R.id.input_content);
        swFavorite = findViewById(R.id.sw_favorite);

        setupNote();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noteId == EMPTY_NOTE){
                    Note note = new Note();
                    note.setName(inputName.getEditText().getText().toString());
                    note.setContent(inputContent.getEditText().getText().toString());
                    note.setFavorite(swFavorite.isChecked());
                    note.setCreatedDate(new Date());
                    notesDatabase.noteDao().insertNote(note);
                }else{
                    Note note = notesDatabase.noteDao().findNote(noteId);
                    note.setName(inputName.getEditText().getText().toString());
                    note.setContent(inputContent.getEditText().getText().toString());
                    note.setFavorite(swFavorite.isChecked());
                    notesDatabase.noteDao().updateNote(note);
                }

                finish();
            }
        });


    }

    private void setupNote() {
        if( noteId != EMPTY_NOTE){
          Note note =  notesDatabase.noteDao().findNote(noteId);
          inputName.getEditText().setText(note.getName());
          inputContent.getEditText().setText(note.getContent());
          swFavorite.setChecked(note.isFavorite());
        }
    }
}
