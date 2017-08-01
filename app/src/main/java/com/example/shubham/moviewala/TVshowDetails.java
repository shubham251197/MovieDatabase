package com.example.shubham.moviewala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham.moviewala.Retrofit.ApiInterface;
import com.example.shubham.moviewala.Retrofit.RetrofitInstance;
import com.example.shubham.moviewala.Retrofit.TVshowDetailsResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TVshowDetails extends AppCompatActivity {

    static int TvId;
    TVshowDetailsResponse tVshowDetailsResponse;
    ImageView backdrop,poster;
    TextView title,genre,overview,production,onAirFirst;
    RatingBar rating_bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);

        Intent i=getIntent();
        TvId=i.getIntExtra("TV_ID",1399);


        backdrop=(ImageView) findViewById(R.id.Backdrop_tv);
        poster=(ImageView) findViewById(R.id.smallpicture_tv);
        title=(TextView) findViewById(R.id.tv_title) ;
        genre=(TextView) findViewById(R.id.genre_textview_tv) ;
        overview=(TextView) findViewById(R.id.overview_tv) ;
        production=(TextView) findViewById(R.id.production_tv) ;
        onAirFirst=(TextView) findViewById(R.id.release_date_tv) ;
        rating_bar=(RatingBar) findViewById(R.id.tv_rating) ;
        fetch_details();
    }


    private void fetch_details(){


        Retrofit retro= RetrofitInstance.GetRetrofitInstance();

        ApiInterface api=retro.create(ApiInterface.class);
        Call<TVshowDetailsResponse> tv_call=api.getTVshowDetails(TvId,"5054967fd9429124090ebee0d4a99aea","language=en-US");
        tv_call.enqueue(new Callback<TVshowDetailsResponse>() {
            @Override
            public void onResponse(Call<TVshowDetailsResponse> call, Response<TVshowDetailsResponse> response) {
                tVshowDetailsResponse=response.body();
                setDetails();
            }

            @Override
            public void onFailure(Call<TVshowDetailsResponse> call, Throwable t) {
                Toast.makeText(TVshowDetails.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }



    void setDetails(){


        String backdrop_url="http://image.tmdb.org/t/p/w300"+tVshowDetailsResponse.getBackdrop_path();
        Picasso.with(this).load(backdrop_url).into(backdrop);

        String poster_url="http://image.tmdb.org/t/p/w300"+tVshowDetailsResponse.getPoster_path();
        Picasso.with(this).load(poster_url).into(poster);

        title.setText(tVshowDetailsResponse.getOriginal_name());
        overview.setText(tVshowDetailsResponse.getOverview());

    }

}
