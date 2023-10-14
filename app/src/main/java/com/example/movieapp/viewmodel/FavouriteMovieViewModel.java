package com.example.movieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapp.database.MovieDao;
import com.example.movieapp.database.MovieDataBase;
import com.example.movieapp.models.Movie;

import java.util.List;

public class FavouriteMovieViewModel extends AndroidViewModel {

    private final MovieDao movieDao;

    public FavouriteMovieViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDataBase
                .getInstance(application)
                .getMovieDao();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieDao.getMovies();
    }
}
