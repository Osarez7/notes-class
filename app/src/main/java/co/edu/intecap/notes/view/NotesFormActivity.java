package co.edu.intecap.notes.view;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.model.database.NotesDatabase;
import co.edu.intecap.notes.model.entities.Note;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputLayout;

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
    private ImageButton ibAddImage;
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
        Intent intent =  getIntent();
        if( intent != null ){
            noteId =  intent.getLongExtra(EXTRA_NOTE_ID, EMPTY_NOTE);
        }


        notesDatabase = NotesDatabase.getInstance(getApplicationContext());

        btnSave = findViewById(R.id.btn_save);

        inputName = findViewById(R.id.input_name);
        inputContent = findViewById(R.id.input_content);
        swFavorite = findViewById(R.id.sw_favorite);
        ivContent = findViewById(R.id.iv_image_conent);

        setupNote();


        ibAddImage = findViewById(R.id.ib_add_image);

        ibAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

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
                    Note note = notesDatabase.noteDao().findNoteById(noteId);
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
          Note note =  notesDatabase.noteDao().findNoteById(noteId);
          if(note != null){
              inputName.getEditText().setText(note.getName());
              inputContent.getEditText().setText(note.getContent());
              swFavorite.setChecked(note.isFavorite());
          }
        }
    }





    private void takePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivContent.setImageBitmap(imageBitmap);
            ivContent.setVisibility(View.VISIBLE);
        }
    }
}
