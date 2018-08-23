package com.example.shubham.moviewala;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham.moviewala.Retrofit.ApiInterface;
import com.example.shubham.moviewala.Retrofit.GenreResposne;
import com.example.shubham.moviewala.Retrofit.MovieResponse;
import com.example.shubham.moviewala.Retrofit.RetrofitInstance;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WatchlistActivity extends AppCompatActivity implements MovieListAdapter.MovieClickListener{


    ArrayList<Movie> mMovies;
    ArrayList<Movie> movieArraylist;
    int[] mScores;
    HashMap<String,Integer> ratingMap;
    RecyclerView Rview;
    ArrayList<Object> genresList;
    MovieListAdapter movieListAdapter;
    Boolean isSuggestion;
    ArrayList<Movie> mSuggestedMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        isSuggestion=getIntent().getBooleanExtra("isSuggestions",false);

        mMovies=new ArrayList<>();
        mSuggestedMovies=new ArrayList<>();
        movieArraylist=new ArrayList<>();

        Rview=(RecyclerView) findViewById(R.id.watchlist_recycle);
        if(isSuggestion) {
            movieListAdapter = new MovieListAdapter(this, movieArraylist, this);
            ratingMap=new HashMap<>();
            genresList=new ArrayList<>();

            TextView text=(TextView) findViewById(R.id.textView);
            text.setText("SUGGESTIONS");
        }
            else
            movieListAdapter= new MovieListAdapter(this,mMovies,this);
        Rview.setAdapter(movieListAdapter);
        Rview.setLayoutManager(new LinearLayoutManager(this));
        Rview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        Rview.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {


                AlertDialog.Builder builder = new AlertDialog.Builder(WatchlistActivity.this);
                builder.setTitle("DELETE");
                builder.setMessage("Do want to delete this from watchlist ? ");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int id=mMovies.get(viewHolder.getAdapterPosition()).id;
                        String str[]={id+""};
                        AppOpenHelper openHelper=new AppOpenHelper(WatchlistActivity.this);
                        SQLiteDatabase database=openHelper.getWritableDatabase();
                        database.delete(AppOpenHelper.DB_TABLENAME,AppOpenHelper.DB_ID+" = ?",str);
                        UpdateList();
                    }
                    });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(Rview);
        UpdateList();


        if(isSuggestion){

            fetchmovies(true);


            Log.i("TAG", "onCreate: "+mSuggestedMovies.size());
            setScore();
            sort(0,movieArraylist.size()-1);
//            mSuggestedMovies.clear();
//            for(Movie m:movieArraylist)
//                mSuggestedMovies.add(m);
//            Log.i("TAG", mSuggestedMovies.size()+"");
//            for(int i:mScores)
//                Log.i("TAG", i+" ");
//            movieListAdapter.notifyDataSetChanged();
        }


    }

    private void setScore() {
        mScores=new int[movieArraylist.size()];
        Log.i("TAG", "setScore: "+movieArraylist.size());
        for(Movie m:movieArraylist){
            int i=movieArraylist.indexOf(m);
            mScores[i]=0;
            for(String genre:m.genre_ids){
                if(ratingMap.containsKey(genre))
                    mScores[i]+=ratingMap.get(genre);
            }
        }

    }


    public void sort(int lo,int hi){
        if(lo>=hi)
            return;
        int right=hi;
        int left=lo;
        int pivet=mScores[(hi+lo)/2];
        while(left<=right){
            while(mScores[left]<pivet)
                left++;
            while(mScores[right]>pivet)
                right--;
            if(left<=right)
            {
                int temp=mScores[left];
                mScores[left]=mScores[right];
                mScores[right]=temp;

                Movie temp2=movieArraylist.get(left);
                movieArraylist.set(left,movieArraylist.get(right));
                movieArraylist.set(right,temp2);

                left++;
                right--;
            }
        }
    }


    private void fetchmovies(Boolean popular) {

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





        Retrofit retrofit= RetrofitInstance.GetRetrofitInstance();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<MovieResponse> call;
        if(popular)
           call=apiInterface.getPopularMovies("5054967fd9429124090ebee0d4a99aea");
        else
            call=apiInterface.getUpcomingMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body()!= null){
                    MovieResponse movieResponse=response.body();
                    movieArraylist=movieResponse.getResults();
                    setListItem(movieArraylist);

                    fetchmovies(false);
                }
                else
                    Toast.makeText(WatchlistActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(WatchlistActivity.this,"NETWORK ERROR",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setListItem(ArrayList<Movie> movies) {

        for(Movie m:movies){
            SetGenre(m);
            if(!movieArraylist.contains(m) && !mMovies.contains(m)) {
                mSuggestedMovies.add(m);
                movieArraylist.set(movies.indexOf(m),m);
            }
            movieListAdapter.notifyDataSetChanged();

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





    private void UpdateList() {

        AppOpenHelper openHelper=new AppOpenHelper(this);
        mMovies.clear();
        SQLiteDatabase database= openHelper.getReadableDatabase();
        Cursor cursor= database.query(AppOpenHelper.DB_TABLENAME,null,null,null,null,null,null);
        while(cursor.moveToNext()){

            int id=cursor.getInt(cursor.getColumnIndex(AppOpenHelper.DB_ID));
            int vote=cursor.getInt(cursor.getColumnIndex(AppOpenHelper.DB_VOTE_AVG));
            int watch=cursor.getInt(cursor.getColumnIndex(AppOpenHelper.DB_WATCHLIST));
            String title=cursor.getString(cursor.getColumnIndex(AppOpenHelper.DB_TITLE));
            String poster=cursor.getString(cursor.getColumnIndex(AppOpenHelper.DB_POSTER));
            String genre=cursor.getString(cursor.getColumnIndex(AppOpenHelper.DB_GENRE));
            boolean watchlist_bool;
            if(watch==0)
                watchlist_bool=false;
            else
                watchlist_bool=true;

            ArrayList<String> str=new ArrayList<>();
            String gen[]=genre.split("-");
            for(int i=0;i<gen.length;i++) {
                if(isSuggestion){
                    if(ratingMap.containsKey(gen[i]))
                        ratingMap.put(gen[i],ratingMap.get(gen[i])+1);
                    else
                        ratingMap.put(gen[i],1);
                }
                str.add(gen[i]);
            }
            Movie m=new Movie(id,title,poster,str,watchlist_bool,vote);
            mMovies.add(m);
            if(!isSuggestion)
            movieListAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent=new Intent(this,MovieDetailsShow.class);
//        String genre=mMovies.get(position).genre_ids.get(0);
//        for(int i=1;i<mMovies.get(position).genre_ids.size();i++)
//            genre=genre+"-"+ mMovies.get(position).genre_ids.get(i);
        intent.putExtra("movie_detail",mMovies.get(position).id+"; ");
        startActivity(intent);

    }

    @Override
    public void onStarButtonClickListener(int position) {

    }
}
