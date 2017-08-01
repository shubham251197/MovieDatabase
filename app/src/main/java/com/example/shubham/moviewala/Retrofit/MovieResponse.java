package com.example.shubham.moviewala.Retrofit;

import com.example.shubham.moviewala.Movie;

import java.util.ArrayList;

/**
 * Created by shubham on 21-07-2017.
 */

public class MovieResponse {


    public ArrayList<Movie> results;

    public ArrayList<Movie> getResults(){return this.results;}

    public void setResults(ArrayList<Movie> movies){
        this.results=movies;
    }
}
