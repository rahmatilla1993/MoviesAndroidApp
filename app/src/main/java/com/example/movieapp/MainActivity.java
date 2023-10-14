package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainViewModel viewModel;
    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        observeViewModel();
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        onClickListeners();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        loadingProgressBar = findViewById(R.id.progressBarLoading);
        movieAdapter = new MovieAdapter();
        moviesRecyclerView.setAdapter(movieAdapter);
    }

    private void onClickListeners() {
        movieAdapter.setOnReachEndListener(() -> viewModel.loadMovies());
        movieAdapter.setOnMovieClickListener(movie -> {
            Intent intent = MovieDetailActivity.newIntent(this, movie);
            startActivity(intent);
        });
    }

    private void observeViewModel() {
        viewModel.getMoviesLD()
                .observe(this, movies -> movieAdapter.setMovies(movies));

        viewModel.getIsLoadedLD()
                .observe(this, isLoading -> {
                    if (isLoading) {
                        loadingProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favouriteItem) {
            Intent intent = FavouriteMovieActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}