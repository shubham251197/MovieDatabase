package com.example.shubham.moviewala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shubham.moviewala.Retrofit.TVshowDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TVDetailsShow extends AppCompatActivity  implements TVdetailAsync.OndownloadCompleteListner{


    String str[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent=getIntent();
        str=intent.getStringExtra("tv_details").split(";");

        String url="https://api.themoviedb.org/3/tv/"+Integer.parseInt(str[0])+"?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US";
        TVdetailAsync tVdetailAsync=new TVdetailAsync();
        tVdetailAsync.execute(url);
        tVdetailAsync.SetOnDownloadcompleteListner(this);

    }





    @Override
    public void OndownloadComplete(TVshowDetailsResponse TVDetail) {

        ImageView backdrop=(ImageView) findViewById(R.id.Backdrop);
        ImageView poster=(ImageView) findViewById(R.id.smallpicture);
        TextView title=(TextView) findViewById(R.id.movieTitle);
        TextView genre=(TextView) findViewById(R.id.genre_textview);
        RatingBar ratingBar=(RatingBar) findViewById(R.id.movie_rating);
        TextView overview=(TextView) findViewById(R.id.overview);
        // TextView production=(TextView) findViewById(R.id.production);
        TextView releaseDate=(TextView) findViewById(R.id.release_date);

        String url_poster="http://image.tmdb.org/t/p/w300"+TVDetail.getPoster_path();
        Picasso.with(this).load(url_poster).into(poster);

        title.setText(TVDetail.getOriginal_name());
        ratingBar.setMax(5);
        ratingBar.setRating(TVDetail.getVote_average()/2);
        overview.setText(TVDetail.getOverview());
        releaseDate.setText(TVDetail.first_air_date);

        String url_backdrop="http://image.tmdb.org/t/p/w300"+TVDetail.getBackdrop_path();
        Picasso.with(this).load(url_backdrop).into(backdrop);

        genre.setText(str[1]);
    }
}
