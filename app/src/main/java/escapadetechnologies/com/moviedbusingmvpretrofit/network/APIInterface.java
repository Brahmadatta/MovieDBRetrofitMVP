package escapadetechnologies.com.moviedbusingmvpretrofit.network;

import escapadetechnologies.com.moviedbusingmvpretrofit.model.MovieListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/popular")
    Call<MovieListResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    //Call<MovieListResponse> getPopularMovies(@Query("api_key") String api_key,@Query("page") int page_no);

}
