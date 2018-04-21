package com.example.shubham.moviewala;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.shubham.moviewala.TabMoviesFragments.PopularMoviesFragment;
import com.example.shubham.moviewala.TabMoviesFragments.TopRatedMoviesFragment;
import com.example.shubham.moviewala.TabMoviesFragments.UpComingMoviesFragment;
import com.example.shubham.moviewala.TabTVshowFragments.OnAirTVshowFragment;
import com.example.shubham.moviewala.TabTVshowFragments.PopularTVshowFragment;
import com.example.shubham.moviewala.TabTVshowFragments.TopRatedTVshowFragment;

public class MainActivity extends AppCompatActivity {

    static private ViewPager viewPager;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.movies:
                    viewPager.removeAllViews();
                    viewPager.setAdapter(null);

                    SectionsPagerAdapterMovie newMovieAdapter=new SectionsPagerAdapterMovie(getSupportFragmentManager());
                    viewPager.setAdapter(newMovieAdapter);
                    return true;
                case R.id.tv_show:
                    viewPager.removeAllViews();
                    viewPager.setAdapter(null);

                    SectionsPagerAdapterTV newTVshowAdapter=new SectionsPagerAdapterTV(getSupportFragmentManager());
                    viewPager.setAdapter(newTVshowAdapter);
                    return true;
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SectionsPagerAdapterMovie moviesSectionsPagerAdapter;
        moviesSectionsPagerAdapter = new SectionsPagerAdapterMovie(getSupportFragmentManager());


        viewPager=(ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(moviesSectionsPagerAdapter);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.Search){
            Intent i=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(i);
        }
        else if(id==R.id.show_watchlist){
            Intent i=new Intent(MainActivity.this,WatchlistActivity.class);
            startActivity(i);
        }

        return true;
    }

    public class SectionsPagerAdapterMovie extends FragmentStatePagerAdapter {

        public SectionsPagerAdapterMovie(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

                if (position == 0)
                    return new PopularMoviesFragment();
                else if (position == 1)
                    return new TopRatedMoviesFragment();
                else
                    return new UpComingMoviesFragment();

        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "POPULAR MOVIES";
                case 1:
                    return "TOP RATED MOVIES";
                case 2:
                    return "UPCOMING MOVIES";
            }
            return null;
        }
    }






    public class SectionsPagerAdapterTV extends FragmentStatePagerAdapter {

        public SectionsPagerAdapterTV(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Log.i("TAG","chal gaya");

            if(position==0)
                return new PopularTVshowFragment();
            else if(position==1)
                return new TopRatedTVshowFragment();
            else
                return new OnAirTVshowFragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "POPULAR TV SHOWS";
                case 1:
                    return "TOP RATED TV SHOWS";
                case 2:
                    return "ON AIR TODAY";
            }
            return null;
        }
    }



}
