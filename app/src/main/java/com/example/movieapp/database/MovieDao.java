package com.example.movieapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieapp.models.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {

    @Query("select * from favourites_movie")
    LiveData<List<Movie>> getMovies();

    @Query("select * from favourites_movie where id = :id")
    LiveData<Movie> getMovieById(int id);

    @Insert
    Completable add(Movie movie);

    @Query("delete from favourites_movie where id = :movieId")
    Completable delete(int movieId);
}
