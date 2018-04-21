package com.example.shubham.moviewala;

import java.util.ArrayList;

/**
 * Created by shubham on 17-07-2017.
 */

public class Movie {

    public int id;
    public String title;
    public String poster_path;
    public ArrayList<String> genre_ids;
    public Boolean WatchList;
    public float vote_average;

    public void setWatchList(Boolean watchList) {
        WatchList = watchList;
    }

    public Boolean getWatchList() {
        return WatchList;
    }

    public Movie(int id, String title, String poster_path, ArrayList<String> genre_ids, Boolean watchList, float vote_average) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.genre_ids = genre_ids;
        WatchList = watchList;
        this.vote_average = vote_average;
    }
}
