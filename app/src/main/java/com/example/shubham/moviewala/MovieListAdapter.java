package com.example.shubham.moviewala;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham.moviewala.TabMoviesFragments.PopularMoviesFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by shubham on 17-07-2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {


    Context mContext;
    ArrayList<Movie> mMovies;
    MovieClickListener mListener;


    public interface MovieClickListener{
        void onItemClickListener(View view,int position);
        void onStarButtonClickListener(int position);
    }

    public MovieListAdapter(Context mContext, ArrayList<Movie> mMovies, MovieClickListener mListener) {
        this.mContext = mContext;
        this.mMovies = mMovies;
        this.mListener = mListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.movie_list_item,parent,false);
        return  new MovieViewHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie m=mMovies.get(position);
        holder.titleText.setText(m.title);
        String genre=m.genre_ids.get(0);
        for(int i=1;i<m.genre_ids.size();i++)
            genre=genre+"-"+ m.genre_ids.get(i);
        holder.genreText.setText(genre);
        String url="http://image.tmdb.org/t/p/w300"+m.poster_path;
        Picasso.with(mContext).load(url).into(holder.Poster);
        Log.i("RATING", String.valueOf((m.vote_average)/2));
        holder.ratingBar.setMax(5);
        holder.ratingBar.setRating((m.vote_average)/2);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        RatingBar ratingBar;
        TextView titleText;
        TextView genreText;
        ImageButton watchList;
        ImageView Poster;
        MovieClickListener movieClickListener;


    public MovieViewHolder(View itemView,MovieClickListener listener) {

        super(itemView);
        itemView.setOnClickListener(this);
        titleText=(TextView) itemView.findViewById(R.id.title_textView);
        genreText=(TextView) itemView.findViewById(R.id.genre_textview);
        watchList=(ImageButton) itemView.findViewById(R.id.watchlist_button);
        Poster=(ImageView) itemView.findViewById(R.id.imageView);
        ratingBar=(RatingBar) itemView.findViewById(R.id.ratingBar);
        movieClickListener=listener;
        watchList.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int id=view.getId();
        int position=getAdapterPosition();
        if(position!= RecyclerView.NO_POSITION){
            if(id==R.id.watchlist_button){
                watchList.setImageResource(android.R.drawable.btn_star_big_on);
                movieClickListener.onStarButtonClickListener(position);
            }
            else if(id==R.id.list_item_layout){
                movieClickListener.onItemClickListener(view,position);
            }
        }

    }
}


}
