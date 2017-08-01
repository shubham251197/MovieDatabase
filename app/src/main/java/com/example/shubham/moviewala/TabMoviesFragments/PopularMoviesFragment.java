package com.example.shubham.moviewala.TabMoviesFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shubham.moviewala.Movie;
import com.example.shubham.moviewala.MovieDetails;
import com.example.shubham.moviewala.MovieListAdapter;
import com.example.shubham.moviewala.Object;
import com.example.shubham.moviewala.R;
import com.example.shubham.moviewala.Retrofit.ApiInterface;
import com.example.shubham.moviewala.Retrofit.GenreResposne;
import com.example.shubham.moviewala.Retrofit.MovieResponse;
import com.example.shubham.moviewala.Retrofit.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shubham on 23-07-2017.
 */

public class PopularMoviesFragment extends Fragment {

    ArrayList<Movie> MovieList;
    RecyclerView Rview;
    MovieListAdapter movieListAdapter;
    ArrayList<Object> genresList;
    ArrayList<Movie> movies;
    int movieID;

    Retrofit retrofit1=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
    ApiInterface apiInterface1=retrofit1.create(ApiInterface.class);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.movies_fragment,container,false);

        Rview=(RecyclerView) v.findViewById(R.id.recycler_view);
        MovieList= new ArrayList<>();

        movieListAdapter= new MovieListAdapter(getContext(), MovieList, new MovieListAdapter.MovieClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

                Snackbar.make(view,"CLICKED",Snackbar.LENGTH_SHORT).show();
                movieID=movies.get(position).id;
                Intent intent=new Intent(getContext(),MovieDetails.class);
                intent.putExtra("movie_detail",movieID);
                startActivity(intent);
            }

            @Override
            public void onStarButtonClickListener(int position) {

                Toast.makeText(getContext(),"ADDED TO WATCHLIST",Toast.LENGTH_SHORT).show();

            }
        });

        Rview.setAdapter(movieListAdapter);
        Rview.setLayoutManager(new LinearLayoutManager(getContext()));
        Rview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        Rview.setItemAnimator(new DefaultItemAnimator());
        fetchmovies();
return v;
    }


    private void fetchmovies() {

        Retrofit retrofit= RetrofitInstance.GetRetrofitInstance();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<MovieResponse> call=apiInterface.getPopularMovies("5054967fd9429124090ebee0d4a99aea");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse=response.body();
                movies=movieResponse.getResults();
                setListItem(movies);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(getContext(),"NETWORK ERROR",Toast.LENGTH_SHORT).show();
            }
        });



        Retrofit retro_genre=RetrofitInstance.GetRetrofitInstance();
        ApiInterface api=retro_genre.create(ApiInterface.class);
        Call<GenreResposne> call_genre=api.getGenres();
        call_genre.enqueue(new Callback<GenreResposne>() {
            @Override
            public void onResponse(Call<GenreResposne> call, Response<GenreResposne> response) {
                genresList=response.body().genres;
//                for(int i=0;i<genresList.size();i++)
//                    Log.i("TAG",genresList.get(i).id+"-"+genresList.get(i).name);
            }

            @Override
            public void onFailure(Call<GenreResposne> call, Throwable t) {

            }
        });



    }

    private void setListItem(ArrayList<Movie> movies) {
        MovieList.clear();
        for(Movie m:movies){
            SetGenre(m);
            MovieList.add(m);
            movieListAdapter.notifyItemInserted(movies.indexOf(m));
        }

    }



    void SetGenre(Movie m){

        for(int i=0;i<m.genre_ids.size();i++){
            for(int j=0;j<genresList.size();j++){
                if(Integer.parseInt(m.genre_ids.get(i))==genresList.get(j).id){
                    m.genre_ids.set(i,genresList.get(j).name);
                    break;
                }
            }
        }

    }





//    public void setGenre(Movie m){
//
//
//        final Movie movie=m;
//        Call<GenreResposne> genreResposneCall=apiInterface1.getGenres(m.id);
//
//        genreResposneCall.enqueue(new Callback<GenreResposne>() {
//            @Override
//            public void onResponse(Call<GenreResposne> call, Response<GenreResposne> response) {
//                genresList=response.body().genres;
//                for(int i=0;i<genresList.size();i++){
//                    movie.genre_ids.set(i,genresList.get(i).name);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GenreResposne> call, Throwable t) {
//
//                Toast.makeText(getContext(),"NETWORK ERROR",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


}
