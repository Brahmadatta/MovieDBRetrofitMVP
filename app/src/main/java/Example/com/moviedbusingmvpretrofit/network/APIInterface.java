package Example.com.moviedbusingmvpretrofit.network;

import Example.com.moviedbusingmvpretrofit.model.Movie;
import Example.com.moviedbusingmvpretrofit.model.MovieListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/popular")
    Call<MovieListResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    //Call<MovieListResponse> getPopularMovies(@Query("api_key") String api_key,@Query("page") int page_no);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("append_to_response") String credits);
    //Call<Movie> getMovieDetails(@Path("movie_id") int movie_id, @Query("api_key") String api_key, @Query("append_to_response") String credits);


}
