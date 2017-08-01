package com.example.shubham.moviewala.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shubham on 31-07-2017.
 */

public class RetrofitInstance {

   public static Retrofit retrofit;

    public static Retrofit GetRetrofitInstance(){
        if(retrofit==null)
            retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;

    }
}
