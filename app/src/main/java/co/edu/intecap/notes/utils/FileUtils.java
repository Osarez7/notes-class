package co.edu.intecap.notes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import androidx.annotation.NonNull;

public class FileUtils {

    private static final int EOF = -1;
    private static int DEFAULT_BUFFER_SIZE = 1024 * 4;
    public static final String TAG = FileUtils.class.getSimpleName();

    public static File createImageFile(Context context) throws IOException {
        String imageFileName = "JPEG_" + new Date().getTime();
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName, ".jpg", storageDir);
        return image;
    }


    public static File createFileFromUri(Context context, Uri uri) {

        File file = null;

        InputStream contentResolverInputStream = null;
        OutputStream contentResolverOutputStream = null;
        try {
            file = createImageFile(context);
            contentResolverInputStream = context.getContentResolver().openInputStream(uri);
            contentResolverOutputStream = new FileOutputStream(file);
            copyFile(contentResolverInputStream, contentResolverOutputStream, new byte[DEFAULT_BUFFER_SIZE]);
        } catch (IOException e) {
            Log.e(TAG, "createExternalStoragePrivateFile: ");
        } finally {
            try {
                if (contentResolverInputStream != null) {
                    contentResolverInputStream.close();
                }
                if (contentResolverOutputStream != null) {
                    contentResolverOutputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            }

        }
        return file;
    }

    public static long copyFile(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


    public static void compressImage(@NonNull String photoPath){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 10;
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bmp= BitmapFactory.decodeFile(photoPath, options);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photoPath);
            bmp.compress(Bitmap.CompressFormat.PNG, 20, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


