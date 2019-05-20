package co.edu.intecap.notes.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesApiClient {

    private static String API_BASE_URL = "https://notes-api-server.herokuapp.com/";
    private static  Retrofit retrofit = null;

    public static  Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}