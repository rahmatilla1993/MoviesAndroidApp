package com.example.movieapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("docs")
    private final List<Movie> movies;

    public MovieResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @NonNull
    @Override
    public String toString() {
        return "ApiResponse{" +
                "movies=" + movies +
                '}';
    }
}
