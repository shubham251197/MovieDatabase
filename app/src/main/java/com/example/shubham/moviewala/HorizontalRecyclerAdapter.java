package com.example.shubham.moviewala;

import android.content.Context;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by shubham on 29-07-2017.
 */



public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.HorizontalViewHolder> {


    Context mContext;
    ArrayList<HorizonalViewObject> mList;
    OnHorizontalitemclickListner mListner;


    public interface OnHorizontalitemclickListner{
        void OnHorizontalItemClick(View v,int position);
    }


    public HorizontalRecyclerAdapter(Context context,ArrayList<HorizonalViewObject> mList,OnHorizontalitemclickListner onHorizontalitemclickListner)
    {
        this.mContext=context;
        this.mList=mList;
        this.mListner=onHorizontalitemclickListner;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.horizontal_view_item,parent,false);
        return new HorizontalViewHolder(v,mListner);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {

        holder.name.setText(mList.get(position).title);
        String url="http://image.tmdb.org/t/p/w300"+mList.get(position).image;
        Picasso.with(mContext).load(url).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class HorizontalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView photo;
        TextView name;
        OnHorizontalitemclickListner ClickListener;

       public HorizontalViewHolder(View itemView,OnHorizontalitemclickListner listner) {
           super(itemView);
           photo=(ImageView) itemView.findViewById(R.id.photo);
           name=(TextView) itemView.findViewById(R.id.name);
           ClickListener=listner;
       }

       @Override
       public void onClick(View view) {

           int pos=getAdapterPosition();
           ClickListener.OnHorizontalItemClick(view,pos);

       }
   }

}
