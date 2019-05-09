package co.edu.intecap.notes.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import java.util.Date;

import androidx.core.content.FileProvider;

public class FileUtils {

    String currentPhotoPath;

    public static File createImageFile(Context context) throws IOException {
        String imageFileName = "JPEG_" + new Date().getTime();
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName, ".jpg", storageDir);
        return image;
    }



}


