package co.edu.intecap.notes.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import co.edu.intecap.notes.model.converters.DateTypeConverter;
import co.edu.intecap.notes.model.dao.NoteDao;
import co.edu.intecap.notes.model.entities.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase intance;
    private static final String DATABASE_NAME = "notes_database";


    public static NotesDatabase getInstance(Context context) {

        if (intance == null) {
            intance =
                    Room.databaseBuilder(context.getApplicationContext(), NotesDatabase.class, DATABASE_NAME)
                            // en este ejemplo vamos a permitir hacer consultas a la base de datos
                            // en el hilo princiapl, no es recomendado en aplicaciones en produccioœ
                            .allowMainThreadQueries()
                            .build();
        }
        return intance;
    }

    public static void destroyInstance() {
        intance = null;
    }



    public abstract NoteDao noteDao();
}

