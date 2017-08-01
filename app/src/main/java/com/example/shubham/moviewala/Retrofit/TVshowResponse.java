package com.example.shubham.moviewala.Retrofit;

import com.example.shubham.moviewala.Movie;
import com.example.shubham.moviewala.TVshow;

import java.util.ArrayList;

/**
 * Created by shubham on 26-07-2017.
 */

public class TVshowResponse {

    public ArrayList<TVshow> results;

    public ArrayList<TVshow> getResults(){return this.results;}

    public void setResults(ArrayList<TVshow> movies){
        this.results=movies;
    }
}
