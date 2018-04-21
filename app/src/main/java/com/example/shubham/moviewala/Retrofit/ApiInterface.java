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

 @GET("search/collection")
    Call<SearchResponse> getSearches(@Query("api_key") String api,@Query("language") String lang,@Query("query") String q);

 @GET("tv/popular?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")

    Call<TVshowResponse> getPopularTVshow();

 @GET("tv/airing_today?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")

    Call<TVshowResponse> getOnAIRtvshow();

 @GET("tv/top_rated?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")

    Call<TVshowResponse> getTopratedTvshow();

 @GET("movie/{id}")

    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int movieId,@Query("api_key") String apikey);

  @GET("tv/{tv_id}")
    Call<TVshowDetailsResponse> getTVshowDetails(@Path("tv_id") int id,@Query("api_key") String api,@Query("language") String lang);

  @GET("movie/{movie_id}/similar?api_key=5054967fd9429124090ebee0d4a99aea&language=en-US&page=1")
    Call<SimilarMoviesResponse> getSimilarMovies(@Path("movie_id") int id);

    @GET("movie/{id}/credits?api_key=5054967fd9429124090ebee0d4a99aea")
    Call<CastResponse> getCast(@Path("id") int id);
}
