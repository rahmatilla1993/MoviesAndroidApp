package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.viewmodel.FavouriteMovieViewModel;

public class FavouriteMovieActivity extends AppCompatActivity {

    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;
    private FavouriteMovieViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movie);
        viewModel = new ViewModelProvider(this).get(FavouriteMovieViewModel.class);
        init();
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        viewModel.getMovies().observe(
                this,
                movies -> movieAdapter.setMovies(movies)
        );
        movieAdapter.setOnMovieClickListener(movie -> {
            Intent intent = MovieDetailActivity.newIntent(this, movie);
            startActivity(intent);
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMovieActivity.class);
    }

    private void init() {
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        movieAdapter = new MovieAdapter();
        moviesRecyclerView.setAdapter(movieAdapter);
    }
}