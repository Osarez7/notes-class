package co.edu.intecap.notes.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
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
    public static File createImageFile(Context context, Uri uri) throws IOException {
        String imageFileName = "JPEG_" + new Date().getTime();
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image =  new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return image;
    }



}


