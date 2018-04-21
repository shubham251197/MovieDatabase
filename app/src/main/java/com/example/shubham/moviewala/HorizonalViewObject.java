package com.example.shubham.moviewala;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 29-07-2017.
 */

public class HorizonalViewObject {

    String poster_path;
    String original_title;
    int id;

    public HorizonalViewObject(String poster_path, String original_title) {
        this.poster_path = poster_path;
        this.original_title = original_title;
    }
}
