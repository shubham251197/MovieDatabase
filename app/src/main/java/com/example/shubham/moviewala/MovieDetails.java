package com.example.shubham.moviewala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham.moviewala.Retrofit.ApiInterface;
import com.example.shubham.moviewala.Retrofit.MovieDetailsResponse;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {


    int id;
    MovieDetailsResponse movieDetailsResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent i=getIntent();
        id=i.getIntExtra("movie_detail",-1);
        Log.i("TAG",id+"");
        fetchDetails();
    }

    private void fetchDetails() {



        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<MovieDetailsResponse> call=apiInterface.getMovieDetails(id,"29213ce3aa2d1e8b18fb60d618ccec21");

        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                movieDetailsResponse=response.body();
                setDetails();

            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

                Toast.makeText(MovieDetails.this, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setDetails() {

        ImageView backdrop=(ImageView) findViewById(R.id.Backdrop);
        ImageView poster=(ImageView) findViewById(R.id.smallpicture);
        TextView title=(TextView) findViewById(R.id.movieTitle);
        TextView genre=(TextView) findViewById(R.id.genre_textview);
        RatingBar ratingBar=(RatingBar) findViewById(R.id.movie_rating);
        TextView overview=(TextView) findViewById(R.id.overview);
        //TextView production=(TextView) findViewById(R.id.production);
        TextView releaseDate=(TextView) findViewById(R.id.release_date);


        title.setText(movieDetailsResponse.getOriginal_title());
        ratingBar.setMax(5);
        ratingBar.setRating(movieDetailsResponse.getVote_average()/2);
        overview.setText(movieDetailsResponse.getOverview());
        releaseDate.setText(movieDetailsResponse.getRelease_date());

        String movie_genre=movieDetailsResponse.genres.get(0).name;
        for(int i=1;i<movieDetailsResponse.genres.size();i++)
        {
            movie_genre+=" - "+movieDetailsResponse.genres.get(i).name;

        }
        genre.setText(movie_genre);

        String movie_production=movieDetailsResponse.production_companies.get(0).name;
        for(int i=1;i<movieDetailsResponse.production_companies.size();i++)
        {
            movie_production+=" - "+movieDetailsResponse.production_companies.get(i).name;

        }
        //production.setText(movie_production);

        String url_backdrop="http://image.tmdb.org/t/p/w300"+movieDetailsResponse.getBackdrop_path();
        Picasso.with(this).load(url_backdrop).into(backdrop);

        String url_poster="http://image.tmdb.org/t/p/w300"+movieDetailsResponse.getPoster_path();
        Picasso.with(this).load(url_poster).into(poster);
    }
}
