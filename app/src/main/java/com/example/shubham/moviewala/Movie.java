package com.example.shubham.moviewala;

import java.util.ArrayList;

/**
 * Created by shubham on 17-07-2017.
 */

public class Movie {

    public int id;
    String title;
    String poster_path;
    public ArrayList<String> genre_ids;
    Boolean WatchList;
    float vote_average;


    public Movie(String title, ArrayList<String> genre) {
        this.title = title;
        this.genre_ids = genre;
        this.WatchList=false;
    }
}
