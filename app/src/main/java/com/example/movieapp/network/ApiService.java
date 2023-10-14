package com.example.movieapp.network;

import com.example.movieapp.models.MovieResponse;
import com.example.movieapp.models.ReviewResponse;
import com.example.movieapp.models.TrailerResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("v1.3/movie")
    Single<MovieResponse> loadMovies(
            @QueryMap Map<String, String> headers,
            @Query("page") int page
    );

    @GET("v1.3/movie/{movieId}")
    Single<TrailerResponse> findMovieById(@Path("movieId") int movieId);

    @GET("v1/review")
    Single<ReviewResponse> getAllReviews(@Query("movieId") int movieId);
}
