package com.example.shubham.moviewala.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shubham on 22-07-2017.
 */

public interface ApiInterface {


 @GET("movie/popular")

    Call<MovieResponse> getPopularMovies(@Query("api_key") String api);

 @GET("movie/top_rated?api_key=5054967fd9429124090ebee0d4a99aea")

    Call<MovieResponse> getTopRatedMovies();

 @GET("movie/upcoming?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")

    Call<MovieResponse> getUpcomingMovies();

 @GET("genre/movie/list?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US")

    Call<GenreResposne> getGenres();

    @GET("genre/tv/list?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US")

    Call<GenreResposne> getTVGenres();

 @GET("tv/popular?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")

    Call<TVshowResponse> getPopularTVshow();

 @GET("tv/airing_today?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")

    Call<TVshowResponse> getOnAIRtvshow();

 @GET("tv/top_rated?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")

    Call<TVshowResponse> getTopratedTvshow();

 @GET("movie/{movie_id}?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US")

    Call<MovieDetailsResponse> getMovieDetails(@Path("movie_id") int id);

  @GET("tv/{tv_id}")
    Call<TVshowDetailsResponse> getTVshowDetails(@Path("tv_id") int id,@Query("api_key") String api,@Query("language") String lang);
}
