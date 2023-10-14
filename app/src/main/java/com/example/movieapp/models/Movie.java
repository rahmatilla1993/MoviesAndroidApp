package com.example.movieapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favourites_movie")
public class Movie implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("year")
    private int year;
    @SerializedName("rating")
    @Embedded
    private Rating rating;
    @SerializedName("poster")
    @Embedded
    private Poster poster;

    public Movie(int id, String type, String name,
                 String description, int year,
                 Rating rating, Poster poster
    ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.year = year;
        this.rating = rating;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public Rating getRating() {
        return rating;
    }

    public Poster getPoster() {
        return poster;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", poster=" + poster +
                '}';
    }
}
