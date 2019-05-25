package co.edu.intecap.notes.model.daos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import co.edu.intecap.notes.model.NoteEntity;

@Dao
public interface NoteDao {
    //CRUD (Create, Retrieve, Update, Delete)
    @Insert
    public void insertNote(NoteEntity note);

    @Query("SELECT * FROM note")
    public List<NoteEntity> findAllNotes();

    @Query("SELECT * FROM note WHERE id=:id")
    public NoteEntity findNote(long id);

    @Update
    public void updateNote(NoteEntity note);

    @Delete
    public void deleteNote(NoteEntity note);
}
