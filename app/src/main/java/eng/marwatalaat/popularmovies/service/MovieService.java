package eng.marwatalaat.popularmovies.service;

import eng.marwatalaat.popularmovies.model.MovieResponse;
import eng.marwatalaat.popularmovies.model.ReviewsResponse;
import eng.marwatalaat.popularmovies.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MarwaTalaat on 12/3/2016.
 */

public interface MovieService {
    @GET("3/movie/{sort_by}")
    public Call<MovieResponse> getMovies(@Path("sort_by") String sortBy , @Query("api_key") String apiKey);


    @GET("3/movie/{movie_id}/reviews")
    public Call<ReviewsResponse> getMovieReviews(@Path("movie_id") String movieId, @Query("api_key")String apiKey);


    @GET("3/movie/{movie_id}/trailers")
    public Call<TrailersResponse> getMovieTrailers(@Path("movie_id") String movieId, @Query("api_key")String apiKey);


}
