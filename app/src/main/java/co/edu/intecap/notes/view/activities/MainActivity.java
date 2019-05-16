package co.edu.intecap.notes.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.network.model.NotesResponse;
import co.edu.intecap.notes.view.listeners.NoteEventListener;
import co.edu.intecap.notes.model.database.NotesDatabase;
import co.edu.intecap.notes.network.model.Note;
import co.edu.intecap.notes.view.NoteAdapter;
import co.edu.intecap.notes.view.presenters.MainPresenter;
import co.edu.intecap.notes.view.views.MainView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteEventListener, MainView {

    private RecyclerView rvNotes;
    private Toolbar toolBar;
    private FloatingActionButton fabAddNote;
    private NoteAdapter adapter;
    private NotesDatabase notesDatabase;
    private MainPresenter mainPresenter;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNotes = findViewById(R.id.rv_notes);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        mainPresenter = new MainPresenter(this);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter( this);
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
    public void onNoteSelected(String noteId) {
        Intent intent =  NotesFormActivity.newIntent(this, noteId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.getAllNotes();
    }

    @Override
    public void onGetAllNotes(List<Note> notes) {
        Log.d(TAG, "onGetAllNotes: ");
//        adapter.setNoteList(notes);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllNotes(NotesResponse notes) {
        Log.d(TAG, "onGetAllNotes: map" + notes.getNotes());
        adapter.setNoteMap(notes.getNotes());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllNotesError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
