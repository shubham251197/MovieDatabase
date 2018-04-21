package com.example.shubham.moviewala;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shubham on 25-07-2017.
 */

public class TVshow {


    @SerializedName("name")
    public String title;
    public String poster_path;
    public int id;
    public ArrayList<String> genre_ids;
    public Boolean WatchList;
    public float vote_average;


    public TVshow(String title, ArrayList<String> genre) {
        this.title = title;
        this.genre_ids = genre;
        this.WatchList=false;
    }
}
