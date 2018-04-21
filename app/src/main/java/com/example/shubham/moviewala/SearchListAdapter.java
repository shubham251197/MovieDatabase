package com.example.shubham.moviewala;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shubham.moviewala.Retrofit.SearchResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by shubham on 02-08-2017.
 */

public class SearchListAdapter extends ArrayAdapter<SearchResponse.result> {

    Context mContext;
    ArrayList<SearchResponse.result> mResults;


    public SearchListAdapter(@NonNull Context context, ArrayList<SearchResponse.result> resultArrayList) {
        super(context, 0);
        this.mContext=context;
        this.mResults=resultArrayList;
    }


    @Override
    public int getCount() {
        return mResults.size();
    }


    static class SearchViewHolder{
        ImageView poster;
        TextView title;

        public SearchViewHolder(ImageView poster, TextView title) {
            this.poster = poster;
            this.title = title;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.search_list_item,null);
            TextView name=(TextView) convertView.findViewById(R.id.search_name);
            ImageView poster=(ImageView) convertView.findViewById(R.id.search_poster);
            SearchViewHolder searchViewHolder=new SearchViewHolder(poster,name);
            convertView.setTag(searchViewHolder);
        }

        SearchResponse.result r=mResults.get(position);

        SearchViewHolder searchViewHolder=(SearchViewHolder) convertView.getTag();
        String url="http://image.tmdb.org/t/p/w300"+r.getPoster_path();
        Picasso.with(mContext).load(url).into(searchViewHolder.poster);
        searchViewHolder.title.setText(r.getName());

        return convertView;
    }
}
