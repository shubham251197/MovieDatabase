package com.example.shubham.moviewala.Retrofit;

import java.util.ArrayList;

/**
 * Created by shubham on 02-08-2017.
 */

public class SearchResponse {



    public ArrayList<result> results;

    public class result{
        int id;

        public int getId() {
            return id;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public String getName() {
            return name;
        }

        String poster_path;
        String name;
    }

}
