package co.edu.intecap.notes.api;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NotesApi{

    //Crear una nota
    @POST("/api/notes")
    public Call<Note>  addNote(@Body Note note);


    //Consultar una nota
    @GET("/api/notes/{id}")
    public Call<Note> getNote(@Path("id") long id) ;


    //Consultar todas las notas
    @GET("/api/notes")
    public Call<List<Note>> getNotes() ;

    //Actualizar una nota
    @PUT("/api/notes/{id}")
    public Call<Note>  updateNote(@Body Note note , @Path("id") long id);


    //Borrar  una nota
    @DELETE("/api/notes/{id}")
    public Call<NoteResponse>  delteNote( @Path("id") long id); //Borrar  una nota

    @Multipart
    @POST("/upload")
    public Call<NoteResponse>  uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);


}
