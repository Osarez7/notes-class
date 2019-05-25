package co.edu.intecap.notes.utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FilesUtils {
    public static File createImage(Context context) throws IOException {
       File filesDir = context.getFilesDir();
       String name = "note_" + new Date().getTime();
       return File.createTempFile(name, ".jpg", filesDir);
    }
}
