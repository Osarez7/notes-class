package co.edu.intecap.notes.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.model.database.NotesDatabase;
import co.edu.intecap.notes.model.entities.Note;
import co.edu.intecap.notes.utils.FileUtils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesFormActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "noteId";

    private static final int REQUEST_IMAGE_CAPTURE = 120;
    public static final int REQUEST_PERMISSION = 200;

    private Button btnSave;
    private TextInputLayout inputName;
    private TextInputLayout inputContent;
    private Switch swFavorite;
    private int EMPTY_NOTE = -1;
    private long noteId = EMPTY_NOTE;

    private NotesDatabase notesDatabase;
    private ImageButton ibAddImage;
    private ImageView ivContent;
    private String imageFilePath = null;


    public static Intent newIntent(Context context, long noteId) {
        Intent intent = new Intent(context, NotesFormActivity.class);
        intent.putExtra(NotesFormActivity.EXTRA_NOTE_ID, noteId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_form);

        setupUI();
        setupEvents();
        processIntent(getIntent());

    }

    private void processIntent(Intent intent) {

        if (intent != null) {
            noteId = intent.getLongExtra(EXTRA_NOTE_ID, EMPTY_NOTE);
            if (noteId != EMPTY_NOTE) {
                setupNote();
            }

            String action = intent.getAction();
            if (Intent.ACTION_SEND.equals(action)) {

                if (intent.getExtras().containsKey(Intent.EXTRA_TEXT)) {
                    String text = intent.getExtras().getString(Intent.EXTRA_TEXT);
                    inputContent.getEditText().setText(text);
                }

                if (intent.getExtras().containsKey(Intent.EXTRA_SUBJECT)) {
                    String subjet = intent.getExtras().getString(Intent.EXTRA_SUBJECT);
                    inputName.getEditText().setText(subjet);
                }

                if (intent.getExtras().containsKey(Intent.EXTRA_STREAM)) {
                    Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                    ivContent.setImageURI(imageUri);
                    ivContent.setVisibility(View.VISIBLE);
                    imageFilePath = FileUtils.createFileFromUri(this, imageUri).getAbsolutePath();
                }

            }
        }
    }


    private void setupEvents() {
        ibAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteId == EMPTY_NOTE) {
                    Note note = new Note();
                    note.setName(inputName.getEditText().getText().toString());
                    note.setContent(inputContent.getEditText().getText().toString());
                    note.setFavorite(swFavorite.isChecked());
                    note.setCreatedDate(new Date());
                    note.setImagePath(imageFilePath);
                    notesDatabase.noteDao().insertNote(note);
                } else {
                    Note note = notesDatabase.noteDao().findNoteById(noteId);
                    note.setName(inputName.getEditText().getText().toString());
                    note.setContent(inputContent.getEditText().getText().toString());
                    note.setFavorite(swFavorite.isChecked());
                    note.setImagePath(imageFilePath);
                    notesDatabase.noteDao().updateNote(note);
                }

                finish();
            }
        });
    }

    private void setupUI() {
        notesDatabase = NotesDatabase.getInstance(getApplicationContext());

        btnSave = findViewById(R.id.btn_save);
        ibAddImage = findViewById(R.id.ib_add_image);
        inputName = findViewById(R.id.input_name);
        inputContent = findViewById(R.id.input_content);
        swFavorite = findViewById(R.id.sw_favorite);
        ivContent = findViewById(R.id.iv_image_conent);
    }


    private void setupNote() {
        Note note = notesDatabase.noteDao().findNoteById(noteId);
        if (note != null) {
            inputName.getEditText().setText(note.getName());
            inputContent.getEditText().setText(note.getContent());
            swFavorite.setChecked(note.isFavorite());
            imageFilePath = note.getImagePath();

            if (imageFilePath != null) {
                ivContent.setImageURI(Uri.parse(imageFilePath));
                ivContent.setVisibility(View.VISIBLE);
            }
        }

    }


    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = FileUtils.createImageFile(this);
                imageFilePath = photoFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                ivContent.setImageURI(Uri.parse(imageFilePath));
                ivContent.setVisibility(View.VISIBLE);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
