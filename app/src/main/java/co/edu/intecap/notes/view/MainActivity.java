package co.edu.intecap.notes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.listeners.NoteEventListener;
import co.edu.intecap.notes.model.database.NotesDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity implements NoteEventListener {

    RecyclerView rvNotes;
    Toolbar toolBar;
    private FloatingActionButton fabAddNote;
    private NoteAdapter adapter;

    NotesDatabase notesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNotes = findViewById(R.id.rv_notes);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);


        //estamos utlizando la base de datos desde  el activity
        // en una app en produccion esta responsabilidad estaria en otro componente
        notesDatabase = NotesDatabase.getInstance(getApplicationContext());

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notesDatabase.noteDao().getAllNotes(), this);
        rvNotes.setAdapter(adapter);

        fabAddNote = findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NotesFormActivity.class);
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
        adapter.setNoteList(notesDatabase.noteDao().getAllNotes());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotesDatabase.destroyInstance();
    }
}
