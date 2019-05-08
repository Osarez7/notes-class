package co.edu.intecap.notes.model.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import co.edu.intecap.notes.model.entities.Note;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    public List<Note> getAllNotes();

    @Query("SELECT * FROM note where note.id = :noteId")
    public Note findNoteById(long noteId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNote(Note note);

    @Update
    public void updateNote(Note note);

    @Delete
    public void deleteNote(Note note);
}
