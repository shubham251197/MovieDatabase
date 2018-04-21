package com.example.shubham.moviewala;

import android.os.AsyncTask;
import android.util.Log;

import com.example.shubham.moviewala.Retrofit.MovieDetailsResponse;
import com.example.shubham.moviewala.Retrofit.TVshowDetailsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by shubham on 19-04-2018.
 */

public class TVdetailAsync extends AsyncTask<String,Void,TVshowDetailsResponse> {


    TVdetailAsync.OndownloadCompleteListner mlistner;

    void SetOnDownloadcompleteListner(TVdetailAsync.OndownloadCompleteListner listner){
        mlistner=listner;
    }


    @Override
    protected TVshowDetailsResponse doInBackground(String... strings) {
        String urlstring=strings[0];

        URL url= null;
        try {
            url = new URL(urlstring);

            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();

            Log.i("CHECK SEARCH ASYNC", "doInBackground entered");

            Scanner scanner= new Scanner(inputStream);
            String str=" ";
            while(scanner.hasNext()){
                str+=scanner.nextLine();
            }

            return GetDetails(str);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }

    private TVshowDetailsResponse GetDetails(String str) {

        try {
            JSONObject Moviejson=new JSONObject(str);
            String title=Moviejson.getString("original_name");
            String poster_path=Moviejson.getString("poster_path");
            String backdrop=Moviejson.getString("backdrop_path");
            String overview=Moviejson.getString("overview");
            String release=Moviejson.getString("first_air_date");
            int vote=Moviejson.getInt("vote_average");
            int id=Moviejson.getInt("id");
            TVshowDetailsResponse m=new TVshowDetailsResponse();
            m.id=id;
            m.original_name=title;
            m.poster_path=poster_path;
            m.overview=overview;
            m.first_air_date=release;
            m.vote_average=vote;
            m.backdrop_path=backdrop;
            return m;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(TVshowDetailsResponse tVshowDetailsResponse) {
        super.onPostExecute(tVshowDetailsResponse);

        if(mlistner!=null){
            mlistner.OndownloadComplete(tVshowDetailsResponse);
        }
    }

    interface  OndownloadCompleteListner{
        void OndownloadComplete(TVshowDetailsResponse TVDetail);
    }


}
