package com.example.movieapp.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieapp.models.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDataBase extends RoomDatabase {

    private static MovieDataBase instance = null;
    private static final String DB_NAME = "app.db";

    public static MovieDataBase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    MovieDataBase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }

    public abstract MovieDao getMovieDao();
}
