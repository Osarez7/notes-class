package co.edu.intecap.notes.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.listeners.NoteEventListener;
import co.edu.intecap.notes.model.NoteEntity;
import co.edu.intecap.notes.model.database.NotesDatabase;
import co.edu.intecap.notes.view.NoteAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NoteEventListener {

    RecyclerView rvNotes;
    Toolbar toolBar;
    private FloatingActionButton fabAddNote;
    private NoteAdapter adapter;
    private NotesDatabase notesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNotes = findViewById(R.id.rv_notes);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        notesDatabase = NotesDatabase.getInstance(this);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(new ArrayList<NoteEntity>(), this);
        rvNotes.setAdapter(adapter);

        fabAddNote = findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesFormActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onNoteSelected(long noteId) {
        Intent intent =  NotesFormActivity.newIntent(this, noteId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setNoteList(notesDatabase.noteDao().findAllNotes());
        adapter.notifyDataSetChanged();
    }
}
