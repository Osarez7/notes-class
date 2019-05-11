package co.edu.intecap.notes.model.daos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import co.edu.intecap.notes.model.Note;

@Dao
public interface NoteDao {
    //CRUD (Create, Retrieve, Update, Delete)
    @Insert
    public void insertNote(Note note);

    @Query("SELECT * FROM note")
    public List<Note> findAllNotes();

    @Query("SELECT * FROM note WHERE id=:id")
    public Note findNote(long id);

    @Update
    public void updateNote(Note note);

    @Delete
    public void deleteNote(Note note);
}
