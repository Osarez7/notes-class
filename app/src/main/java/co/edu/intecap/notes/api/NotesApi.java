package co.edu.intecap.notes.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotesApi{

    //Crear una nota
    @POST("/api/notes")
    public Call<Note>  addNote(@Body Note note);


    //Consultar una nota
    @GET("/api/notes/{id}")
    public Call<Note> updateNote(@Path("id") long id) ;


    //Consultar todas las notas
    @GET("/api/notes")
    public Call<List<Note>> getNotes(@Path("id") long id) ;

    //Actualizar una nota
    @PUT("/api/notes/{id}")
    public Call<Note>  updateNote(@Body Note note , @Path("id") long id);


    //Borrar  una nota
    @DELETE("/api/notes/{id}")
    public Call<NoteResponse>  delteNote( @Path("id") long id);
}
