package com.example.movieapp.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnMovieClickListener onMovieClickListener;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        Picasso
                .with(holder.itemView.getContext())
                .load(movie.getPoster().getUrl())
                .into(holder.movieImageView);

        double ratingValue = movie.getRating().getRatingKp();
        int backGroundId;
        if (ratingValue > 7) {
            backGroundId = R.drawable.bg_circle_green;
        } else if (ratingValue > 5) {
            backGroundId = R.drawable.bg_circle_orange;
        } else {
            backGroundId = R.drawable.bg_circle_red;
        }
        Drawable drawable = ContextCompat
                .getDrawable(holder.itemView.getContext(), backGroundId);
        holder.ratingTextView.setBackground(drawable);
        holder.ratingTextView.setText(String.format("%.1f", ratingValue));

        if (position >= movies.size() - 5 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(view -> {
            if (onMovieClickListener != null) {
                onMovieClickListener.onMovieClick(movie);
            }
        });
    }

    public interface OnReachEndListener {
        void onReachEnd();
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView movieImageView;
        private final TextView ratingTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movieImageView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
        }
    }
}
