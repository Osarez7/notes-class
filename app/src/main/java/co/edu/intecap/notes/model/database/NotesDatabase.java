package co.edu.intecap.notes.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import co.edu.intecap.notes.converters.DateConverter;
import co.edu.intecap.notes.model.Note;
import co.edu.intecap.notes.model.daos.NoteDao;

@Database(entities = {Note.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public  abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;
    private static final String NOTES_DATABASE = "notes_database";

    protected NotesDatabase(){}

    public static NotesDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NotesDatabase.class,
                    NOTES_DATABASE)
                    //No se utiliza en produccion
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public static void destroyInstace(){
        instance = null;
    }

    public abstract NoteDao noteDao();
}
