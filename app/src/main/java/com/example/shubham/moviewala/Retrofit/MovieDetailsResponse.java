package com.example.shubham.moviewala.Retrofit;

import com.example.shubham.moviewala.Object;

import java.util.ArrayList;

/**
 * Created by shubham on 29-07-2017.
 */

public class MovieDetailsResponse {

    String backdrop_path;
    int id;
    String overview;
    String original_title;
    String poster_path;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }



    public String getRelease_date() {
        return release_date;
    }

    public int getVote_average() {
        return vote_average;
    }

    public ArrayList<Object> production_companies;
    public ArrayList<Object> genres;
    String release_date;
    int vote_average;




}
