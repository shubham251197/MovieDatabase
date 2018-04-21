package com.example.shubham.moviewala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham.moviewala.Retrofit.ApiInterface;
import com.example.shubham.moviewala.Retrofit.CastResponse;
import com.example.shubham.moviewala.Retrofit.MovieDetailsResponse;
import com.example.shubham.moviewala.Retrofit.RetrofitInstance;
import com.example.shubham.moviewala.Retrofit.SimilarMoviesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsShow extends AppCompatActivity implements MovieDetailsAsync.OndownloadCompleteListner{


    String str[];
    ArrayList<HorizonalViewObject> similarMovies;
    ArrayList<HorizonalViewObject> castList;
    HorizontalRecyclerAdapter similarMoviesAdapter,castListAdapter;
    RecyclerView Rsimilar,Rcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent=getIntent();
        str=intent.getStringExtra("movie_detail").split(";");

        String url="https://api.themoviedb.org/3/movie/"+Integer.parseInt(str[0])+"?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US";
        MovieDetailsAsync movieDetailsAsync=new MovieDetailsAsync();
        movieDetailsAsync.execute(url);
        movieDetailsAsync.SetOnDownloadcompleteListner(this);

        similarMovies=new ArrayList<>();
        similarMoviesAdapter= new HorizontalRecyclerAdapter(this,similarMovies, new HorizontalRecyclerAdapter.OnHorizontalitemclickListner() {
            @Override
            public void OnHorizontalItemClick(View v, int position) {

                Intent i=new Intent(MovieDetailsShow.this,MovieDetailsShow.class);
                i.putExtra("movie_detail",similarMovies.get(position).id+"; ");
                startActivity(i);

            }
        });


        castList=new ArrayList<>();
        castListAdapter= new HorizontalRecyclerAdapter(this, castList, new HorizontalRecyclerAdapter.OnHorizontalitemclickListner() {
            @Override
            public void OnHorizontalItemClick(View v, int position) {

            }
        });

        Rcast=(RecyclerView) findViewById(R.id.cast);
        Rcast.setAdapter(castListAdapter);
        Rcast.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        Rcast.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        Rcast.setItemAnimator(new DefaultItemAnimator());

        setCast(str[0]);


        Rsimilar=(RecyclerView) findViewById(R.id.similar_movies);
        Rsimilar.setAdapter(similarMoviesAdapter);
        Rsimilar.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        Rsimilar.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        Rsimilar.setItemAnimator(new DefaultItemAnimator());

        SetSimilarMovies(str[0]);
    }

    private void SetSimilarMovies(String s) {

        Retrofit retroSimilar= RetrofitInstance.GetRetrofitInstance();
        ApiInterface apiInterface=retroSimilar.create(ApiInterface.class);
        Call<SimilarMoviesResponse> call=apiInterface.getSimilarMovies(Integer.parseInt(s));
        call.enqueue(new Callback<SimilarMoviesResponse>() {
            @Override
            public void onResponse(Call<SimilarMoviesResponse> call, Response<SimilarMoviesResponse> response) {

                similarMovies.addAll(response.body().results);
              //  Log.i("TAG",similarMovies.get(0).image);
                similarMoviesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<SimilarMoviesResponse> call, Throwable t) {

                Toast.makeText(MovieDetailsShow.this, "Connection Failure", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setCast(String s){
        Retrofit retroSimilar= RetrofitInstance.GetRetrofitInstance();
        ApiInterface apiInterface=retroSimilar.create(ApiInterface.class);
        Call<CastResponse> call=apiInterface.getCast(Integer.parseInt(s));
        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {

                CastResponse cr=response.body();
                for(int i=0;i<cr.cast.size();i++){
                    castList.add(new HorizonalViewObject(cr.cast.get(i).profile_path,cr.cast.get(i).name));
                }
                castListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Toast.makeText(MovieDetailsShow.this, "Connection Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public void OndownloadComplete(MovieDetailsResponse movieDetail) {


        ImageView backdrop=(ImageView) findViewById(R.id.Backdrop);
        ImageView poster=(ImageView) findViewById(R.id.smallpicture);
        TextView title=(TextView) findViewById(R.id.movieTitle);
        TextView genre=(TextView) findViewById(R.id.genre_textview);
        RatingBar ratingBar=(RatingBar) findViewById(R.id.movie_rating);
        TextView overview=(TextView) findViewById(R.id.overview);
       // TextView production=(TextView) findViewById(R.id.production);
        TextView releaseDate=(TextView) findViewById(R.id.release_date);

        String url_poster="http://image.tmdb.org/t/p/w300"+movieDetail.getPoster_path();
        Picasso.with(this).load(url_poster).into(poster);

        title.setText(movieDetail.getOriginal_title());
        ratingBar.setMax(5);
        ratingBar.setRating(movieDetail.getVote_average()/2);
        overview.setText(movieDetail.getOverview());
        releaseDate.setText(movieDetail.getRelease_date());

        String url_backdrop="http://image.tmdb.org/t/p/w300"+movieDetail.getBackdrop_path();
        Picasso.with(this).load(url_backdrop).into(backdrop);

        genre.setText(str[1]);
    }


}
