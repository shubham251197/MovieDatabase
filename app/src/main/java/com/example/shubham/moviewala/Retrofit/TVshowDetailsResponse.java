package com.example.shubham.moviewala.Retrofit;

import com.example.shubham.moviewala.Object;

import java.util.ArrayList;

/**
 * Created by shubham on 31-07-2017.
 */

public class TVshowDetailsResponse {

    int id;
    String backdrop_path;
    String first_air_date;

    public int getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getVote_average() {
        return vote_average;
    }

    ArrayList<Object> genres;
    String original_name;
    String overview;
    String poster_path;
    ArrayList<Object> production_companies;
    int vote_average;

}
