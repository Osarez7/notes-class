package co.edu.intecap.notes.model.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import co.edu.intecap.notes.model.entities.NoteEntity;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    public List<NoteEntity> getAllNotes();

    @Query("SELECT * FROM note where id = :noteId")
    public NoteEntity findNoteById(long noteId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNote(NoteEntity noteEntity);

    @Update
    public void updateNote(NoteEntity noteEntity);

    @Delete
    public void deleteNote(NoteEntity noteEntity);
}
