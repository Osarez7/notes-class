package co.edu.intecap.notes.network.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesApiClient {


    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}



