package com.example.movieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersList {
    @SerializedName("trailers")
    private List<Trailer> trailerList;

    public TrailersList(List<Trailer> trailers) {
        this.trailerList = trailers;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    @Override
    public String toString() {
        return "Trailers{" +
                "trailers=" + trailerList +
                '}';
    }
}
