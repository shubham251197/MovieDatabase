package com.example.shubham.moviewala.Retrofit;

import com.example.shubham.moviewala.Object;

import java.util.ArrayList;

/**
 * Created by shubham on 29-07-2017.
 */

public class MovieDetailsResponse {

    public String backdrop_path;
    public int id;
    public String overview;
    public String original_title;
    public String poster_path;

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
    public String release_date;
    public int vote_average;




}
