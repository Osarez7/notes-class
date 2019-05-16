package co.edu.intecap.notes.network.model;

public class ApiUtils {
 
    private ApiUtils() {}
    private static final String BASE_URL = "https://notes-69b08.firebaseapp.com/";

    public static NoteApiService getAPIService() {
        return NotesApiClient.getClient(BASE_URL).create(NoteApiService.class);
    }
}