package co.edu.intecap.notes.utils;

import android.os.AsyncTask;

import co.edu.intecap.notes.listeners.CompressionCallback;

public class CompressImageTask extends AsyncTask<String, Void, Void> {
    CompressionCallback compressionCallback;

    public void setCompressionCallback(CompressionCallback compressionCallback) {
        this.compressionCallback = compressionCallback;
    }

    @Override
    protected Void doInBackground(String... parameters) {
        String imagePath = parameters[0];
        FileUtils.compressImage(imagePath);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        compressionCallback.onImageCompressed();
    }
}
