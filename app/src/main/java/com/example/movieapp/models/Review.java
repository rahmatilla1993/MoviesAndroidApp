package com.example.movieapp.models;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("movieId")
    private int movieId;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("review")
    private String content;
    @SerializedName("author")
    private String author;

    public Review(int movieId, String title, String type, String content, String author) {
        this.movieId = movieId;
        this.title = title;
        this.type = type;
        this.content = content;
        this.author = author;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Review{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
