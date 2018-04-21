package com.example.shubham.moviewala;

import android.content.Context;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shubham on 26-07-2017.
 */

public class TVshowAdapter extends RecyclerView.Adapter<TVshowAdapter.TVshowViewHolder> {



    Context mContext;
    ArrayList<TVshow> mTVshow;
    TVshowAdapter.TVshowClickListener mListener;


    public interface TVshowClickListener{
        void onItemClickListener(View view,int position);
        void onStarButtonClickListener(int position);
    }

    public TVshowAdapter(Context mContext, ArrayList<TVshow> mMovies, TVshowAdapter.TVshowClickListener mListener) {
        this.mContext = mContext;
        this.mTVshow = mMovies;
        this.mListener = mListener;
    }



    @Override
    public TVshowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.movie_list_item,parent,false);
        return  new TVshowViewHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(TVshowViewHolder holder, int position) {


        TVshow m=mTVshow.get(position);
        holder.titleText.setText(m.title);
        String genre;
        if(!m.genre_ids.isEmpty()) {
            genre = m.genre_ids.get(0);
            for (int i = 1; i < m.genre_ids.size(); i++)
                genre = genre + "-" + m.genre_ids.get(i);
            holder.genreText.setText(genre);
        }
        else
        holder.genreText.setText("");
        String url="http://image.tmdb.org/t/p/w300"+m.poster_path;
        Picasso.with(mContext).load(url).into(holder.Poster);
      //  Log.i("RATING", String.valueOf((m.vote_average)/2));
        holder.ratingBar.setMax(5);
        holder.ratingBar.setRating((m.vote_average)/2);
    }

    @Override
    public int getItemCount() {
        return mTVshow.size();
    }

    public class TVshowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RatingBar ratingBar;
        TextView titleText;
        TextView genreText;
        ImageButton watchList;
        ImageView Poster;
       TVshowAdapter.TVshowClickListener TVshowClickListener;



        public TVshowViewHolder(View itemView, TVshowAdapter.TVshowClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleText=(TextView) itemView.findViewById(R.id.title_textView);
            genreText=(TextView) itemView.findViewById(R.id.genre_textview);
            watchList=(ImageButton) itemView.findViewById(R.id.watchlist_button);
            Poster=(ImageView) itemView.findViewById(R.id.imageView);
            ratingBar=(RatingBar) itemView.findViewById(R.id.ratingBar);
            TVshowClickListener= mListener;
            watchList.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            int id=view.getId();
            int position=getAdapterPosition();
            if(position!= RecyclerView.NO_POSITION){
                if(id==R.id.watchlist_button){
                    watchList.setImageResource(android.R.drawable.btn_star_big_on);
                    Snackbar.make(view,"ADDED TO WATCHLIST",Snackbar.LENGTH_SHORT).show();
                    TVshowClickListener.onStarButtonClickListener(position);
                }
                else if(id==R.id.list_item_layout){
                    TVshowClickListener.onItemClickListener(view,position);
                }
            }
        }
    }
}
