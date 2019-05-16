package co.edu.intecap.notes.view.presenters;

import android.util.Log;

import java.util.List;

import co.edu.intecap.notes.network.model.ApiUtils;
import co.edu.intecap.notes.network.model.NotesResponse;
import co.edu.intecap.notes.view.views.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private  MainView mainView;

    public MainPresenter(MainView view) {
        mainView = view;
    }

    public  void getAllNotes(){
        ApiUtils.getAPIService().getAllNotes().enqueue(new Callback<NotesResponse>() {
            @Override
            public void onResponse(Call<NotesResponse> call, Response<NotesResponse> response) {
                Log.d("!!!!Response", "onResponse: " + response.toString());
                mainView.onGetAllNotes(response.body());
            }

            @Override
            public void onFailure(Call<NotesResponse> call, Throwable t) {
                Log.e("!!!!Response error", "onResponse: " + t.getMessage());

            }
        });
    }
}
