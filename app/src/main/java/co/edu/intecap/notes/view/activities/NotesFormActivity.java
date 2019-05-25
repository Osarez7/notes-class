package co.edu.intecap.notes.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.model.NoteEntity;
import co.edu.intecap.notes.model.database.NotesDatabase;
import co.edu.intecap.notes.utils.FilesUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class NotesFormActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "noteId";
    private static final int REQUEST_IMAGE_CAPTURE = 120;
    private Button btnSave;
    private TextInputLayout inputName;
    private TextInputLayout inputContent;
    private Switch swFavorite;
    private int EMPTY_NOTE = -1;
    private long noteId = EMPTY_NOTE;

    private NotesDatabase notesDatabase;
    private ImageButton imageBtnPhoto;
    private String imagePath;
    private ImageView ivContent;

    public static Intent newIntent(Context context, long noteId) {
        Intent intent = new Intent(context, NotesFormActivity.class);
        intent.putExtra(NotesFormActivity.EXTRA_NOTE_ID, noteId);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_form);
        final Intent intent = getIntent();
        if (intent != null) {
            noteId = intent.getLongExtra(EXTRA_NOTE_ID, EMPTY_NOTE);
        }

        notesDatabase = NotesDatabase.getInstance(this);

        btnSave = findViewById(R.id.btn_save);

        inputName = findViewById(R.id.input_name);
        inputContent = findViewById(R.id.input_content);
        swFavorite = findViewById(R.id.sw_favorite);
        imageBtnPhoto = findViewById(R.id.imageBtnPhoto);
        ivContent = findViewById(R.id.iv_content);

        setupNote();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputName.getEditText().getText() == null  || inputName.getEditText().getText().toString().isEmpty()){

                    Toast.makeText(NotesFormActivity.this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (noteId == EMPTY_NOTE) {
                    NoteEntity note = new NoteEntity();
                    note.setName(inputName.getEditText().getText().toString());
                    note.setContent(inputContent.getEditText().getText().toString());
                    note.setFavorite(swFavorite.isChecked());
                    note.setCreatedDate(new Date());
                    note.setImagePath(imagePath);
                    notesDatabase.noteDao().insertNote(note);
                } else {
                    NoteEntity note = notesDatabase.noteDao().findNote(noteId);
                    note.setName(inputName.getEditText().getText().toString());
                    note.setContent(inputContent.getEditText().getText().toString());
                    note.setFavorite(swFavorite.isChecked());
                    if(imagePath != null){
                        note.setImagePath(imagePath);
                    }
                    notesDatabase.noteDao().updateNote(note);
                }

                finish();
            }
        });


        imageBtnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intentPhoto.resolveActivity(getPackageManager()) != null) {

                    try {

                        File photoImage = FilesUtils.createImage(NotesFormActivity.this);
                        Uri uri = FileProvider.getUriForFile(NotesFormActivity.this, getPackageName() + ".provider", photoImage);
                        imagePath =  photoImage.getAbsolutePath();
                        intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intentPhoto, REQUEST_IMAGE_CAPTURE);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Image capture", Toast.LENGTH_SHORT).show();
                ivContent.setImageURI(Uri.parse(imagePath));
            }

        }
    }

    private void setupNote() {
        if (noteId != EMPTY_NOTE) {
            NoteEntity note = notesDatabase.noteDao().findNote(noteId);
            inputName.getEditText().setText(note.getName());
            inputContent.getEditText().setText(note.getContent());
            swFavorite.setChecked(note.isFavorite());
            if(note.getImagePath() != null){
                ivContent.setImageURI(Uri.parse(note.getImagePath()));
            }

        }
    }
}
