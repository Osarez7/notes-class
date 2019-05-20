package co.edu.intecap.notes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.api.Note;
import co.edu.intecap.notes.api.NoteResponse;
import co.edu.intecap.notes.api.NotesApi;
import co.edu.intecap.notes.api.NotesApiClient;
import co.edu.intecap.notes.listeners.NoteEventListener;
import co.edu.intecap.notes.model.database.NotesDatabase;
import co.edu.intecap.notes.model.entities.NoteEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NoteEventListener {

    RecyclerView rvNotes;
    Toolbar toolBar;
    private FloatingActionButton fabAddNote;
    private NoteAdapter adapter;

    private NotesApi notesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNotes = findViewById(R.id.rv_notes);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);


        notesApi = NotesApiClient.getClient().create(NotesApi.class);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(new ArrayList<Note>(), this);
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
    public void onDeleteNote(long noteId) {
        notesApi.delteNote(noteId).enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                updateNotes();
            }

            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                Log.e("Note", "onFailure: ",  t);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        updateNotes();
    }

    private void updateNotes() {
        notesApi.getNotes().enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                adapter.setNoteEntityList(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Log.e("Note", "getNotes onFailure: ", t);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotesDatabase.destroyInstance();
    }
}
