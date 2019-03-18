package com.example.gamebacklog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private GameRepository mRepository;
    private LiveData<List<Game>> mGameList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new GameRepository(application.getApplicationContext());
        mGameList = mRepository.getAllGames();
    }

    public LiveData<List<Game>> getGames() {
        return mGameList;
    }

    public void insert(Game game) {
        mRepository.insert(game);
    }

    public void update(Game game) {
        mRepository.update(game);
    }

    public void deleteAllGames() {
        mRepository.deleteAllGames();
    }

    public void delete(Game game) {
        mRepository.delete(game);
    }


}
