package com.example.shubham.moviewala.TabTVshowFragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shubham.moviewala.AppOpenHelper;
import com.example.shubham.moviewala.Movie;
import com.example.shubham.moviewala.Object;
import com.example.shubham.moviewala.R;
import com.example.shubham.moviewala.Retrofit.ApiInterface;
import com.example.shubham.moviewala.Retrofit.GenreResposne;
import com.example.shubham.moviewala.Retrofit.RetrofitInstance;
import com.example.shubham.moviewala.Retrofit.TVshowResponse;
import com.example.shubham.moviewala.TVDetailsShow;
import com.example.shubham.moviewala.TVshow;
import com.example.shubham.moviewala.TVshowAdapter;
import com.example.shubham.moviewala.TVshowDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shubham on 27-07-2017.
 */

public class TopRatedTVshowFragment extends Fragment {

    ArrayList<TVshow> MovieList;
    RecyclerView Rview;
    TVshowAdapter tVshowAdapter;
    ArrayList<Object> genresList;

    Retrofit retrofit1= RetrofitInstance.GetRetrofitInstance();
    ApiInterface apiInterface1=retrofit1.create(ApiInterface.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.movies_fragment,container,false);

        Rview=(RecyclerView) v.findViewById(R.id.recycler_view);
        MovieList= new ArrayList<>();

        tVshowAdapter= new TVshowAdapter(getContext(), MovieList, new TVshowAdapter.TVshowClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

                Snackbar.make(view,"CLICKED",Snackbar.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), TVDetailsShow.class);

                String genre=MovieList.get(position).genre_ids.get(0);
                for(int i=1;i<MovieList.get(position).genre_ids.size();i++)
                    genre=genre+"-"+ MovieList.get(position).genre_ids.get(i);

                intent.putExtra("tv_details",MovieList.get(position).id+";"+genre);
                startActivity(intent);
            }

            @Override
            public void onStarButtonClickListener(int position) {

                AppOpenHelper openHelper=new AppOpenHelper(getContext());
                SQLiteDatabase database= openHelper.getWritableDatabase();
                ContentValues cv=new ContentValues();

                String genre=MovieList.get(position).genre_ids.get(0);
                for(int i=1;i<MovieList.get(position).genre_ids.size();i++)
                    genre=genre+"-"+ MovieList.get(position).genre_ids.get(i);


                cv.put(AppOpenHelper.DB_TITLE,MovieList.get(position).title);
                cv.put(AppOpenHelper.DB_POSTER,MovieList.get(position).poster_path);
                cv.put(AppOpenHelper.DB_GENRE,genre);
                cv.put(AppOpenHelper.DB_ID,MovieList.get(position).id);
                cv.put(AppOpenHelper.DB_VOTE_AVG,(int)MovieList.get(position).vote_average);
                cv.put(AppOpenHelper.DB_WATCHLIST,1);


                int flag=0;
                Cursor cursor= database.query(AppOpenHelper.DB_TABLENAME,null,null,null,null,null,null);
                while (cursor.moveToNext()){
                    int id=cursor.getInt(cursor.getColumnIndex(AppOpenHelper.DB_ID));
                    if(id==MovieList.get(position).id){
                        flag=1;
                        break;
                    }
                }
                if(flag==0)
                    database.insert(AppOpenHelper.DB_TABLENAME,null,cv);

            }
        });

        Rview.setAdapter(tVshowAdapter);
        Rview.setLayoutManager(new LinearLayoutManager(getContext()));
        Rview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        Rview.setItemAnimator(new DefaultItemAnimator());
        fetchmovies();
        return v;
    }


    private void fetchmovies() {

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<TVshowResponse> call=apiInterface.getTopratedTvshow();

        call.enqueue(new Callback<TVshowResponse>() {
            @Override
            public void onResponse(Call<TVshowResponse> call, Response<TVshowResponse> response) {
                TVshowResponse movieResponse=response.body();
                ArrayList<TVshow> movies=movieResponse.getResults();

                setListItem(movies);
            }

            @Override
            public void onFailure(Call<TVshowResponse> call, Throwable t) {
                Toast.makeText(getContext(),"NETWORK ERROR",Toast.LENGTH_SHORT).show();
            }

        });



        Retrofit retro_genre=RetrofitInstance.GetRetrofitInstance();
        ApiInterface api=retro_genre.create(ApiInterface.class);
        Call<GenreResposne> call_genre=api.getTVGenres();
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

    private void setListItem(ArrayList<TVshow> movies) {
        MovieList.clear();
        for(TVshow m:movies){
            SetGenre(m);
            MovieList.add(m);
            tVshowAdapter.notifyItemInserted(movies.indexOf(m));
        }

    }


    void SetGenre(TVshow m){

        for(int i=0;i<m.genre_ids.size();i++){
            for(int j=0;j<genresList.size();j++){
                if(Integer.parseInt(m.genre_ids.get(i))==genresList.get(j).id){
                    m.genre_ids.set(i,genresList.get(j).name);
                    break;
                }
            }
        }

    }


//    public void setGenre(TVshow m){
//
//
//        final TVshow movie=m;
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
