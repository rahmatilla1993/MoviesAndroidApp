package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adapter.ReviewAdapter;
import com.example.movieapp.adapter.TrailerAdapter;
import com.example.movieapp.models.Movie;
import com.example.movieapp.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView titleTextView;
    private TextView yearTextView;
    private TextView descriptionTextView;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private RecyclerView trailersRecyclerView;
    private RecyclerView reviewsRecyclerView;
    private ImageView starImageView;

    private static final String EXTRA_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity";
    private MovieDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        observeViewModel(movie);
        setData(movie);
        viewModel.loadMovieById(movie.getId());
        viewModel.loadAllReviews(movie.getId());

        trailerAdapter.setOnTrailerClickListener(trailer -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(trailer.getUrl()));
            startActivity(intent);
        });
    }

    private void observeViewModel(Movie movie) {
        viewModel.getTrailersListLD().observe(
                this,
                trailersList -> trailerAdapter.setTrailers(trailersList.getTrailerList())
        );

        viewModel.getReviewsListLD().observe(
                this,
                reviews -> reviewAdapter.setReviewList(reviews)
        );

        Drawable starBigOn = ContextCompat.getDrawable(this, android.R.drawable.star_big_on);
        Drawable starBigOff = ContextCompat.getDrawable(this, android.R.drawable.star_big_off);
        viewModel.getMovieById(movie.getId()).observe(
                this,
                movieFromDb -> {
                    if (movieFromDb != null) {
                        starImageView.setImageDrawable(starBigOn);
                        starImageView.setOnClickListener(view -> viewModel.deleteMovie(movie.getId()));
                    } else {
                        starImageView.setImageDrawable(starBigOff);
                        starImageView.setOnClickListener(view -> viewModel.addMovie(movie));
                    }
                }
        );
    }

    private void setData(Movie movie) {
        Picasso
                .with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);

        titleTextView.setText(movie.getName());
        yearTextView.setText(String.valueOf(movie.getYear()));
        descriptionTextView.setText(movie.getDescription());
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        titleTextView = findViewById(R.id.titleTextView);
        yearTextView = findViewById(R.id.yearTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        trailersRecyclerView = findViewById(R.id.trailersRecyclerView);
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        starImageView = findViewById(R.id.starImageView);
        trailerAdapter = new TrailerAdapter();
        reviewAdapter = new ReviewAdapter();
        trailersRecyclerView.setAdapter(trailerAdapter);
        reviewsRecyclerView.setAdapter(reviewAdapter);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}