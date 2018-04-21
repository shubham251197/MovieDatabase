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
import android.view.View;

import java.util.ArrayList;

public class WatchlistActivity extends AppCompatActivity implements MovieListAdapter.MovieClickListener{


    ArrayList<Movie> mMovies;
    RecyclerView Rview;
    MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        mMovies=new ArrayList<>();
        Rview=(RecyclerView) findViewById(R.id.watchlist_recycle);
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
            for(int i=0;i<gen.length;i++)
                str.add(gen[i]);

            Movie m=new Movie(id,title,poster,str,watchlist_bool,vote);
            mMovies.add(m);
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
