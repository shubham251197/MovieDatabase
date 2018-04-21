package com.example.shubham.moviewala;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shubham.moviewala.Retrofit.ApiInterface;
import com.example.shubham.moviewala.Retrofit.RetrofitInstance;
import com.example.shubham.moviewala.Retrofit.SearchResponse;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {


    ListView searchList;
    SearchListAdapter namesAdapter;
    ArrayList<SearchResponse.result> results_list;
    MaterialSearchView searchView;

    Retrofit retro_search;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        results_list=new ArrayList<>();
        namesAdapter=new SearchListAdapter(this,results_list);
        searchList=(ListView) findViewById(R.id.search_list);
        searchList.setAdapter(namesAdapter);

        retro_search= RetrofitInstance.GetRetrofitInstance();
        apiInterface=retro_search.create(ApiInterface.class);

        searchView=(MaterialSearchView) findViewById(R.id.material_search);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty())
                    SearchHit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty())
                    SearchHit(newText);
                else
                    results_list.clear();
                namesAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }




    private void SearchHit(String query){

        Call<SearchResponse> call = apiInterface.getSearches("5054967fd9429124090ebee0d4a99aea","en-US",query);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                results_list.clear();
                results_list.addAll(response.body().results);

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(SearchActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.material_searchview,menu);

        MenuItem item=menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }




}
