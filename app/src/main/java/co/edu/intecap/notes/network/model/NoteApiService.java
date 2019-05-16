package co.edu.intecap.notes.network.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NoteApiService {
    @POST("api/v1/notes")
    Call<Response> saveNote(@Body Note body);

    @GET("api/v1/notes/{noteId}")
    Call<Note> getNote(@Path("noteId") String noteId);

    @GET("api/v1/notes")
    Call<NotesResponse> getAllNotes();

    @PUT("api/v1/notes/{noteId}")
    Call<String> updateNote(@Path("noteId") String noteId, @Body Note note);
}
