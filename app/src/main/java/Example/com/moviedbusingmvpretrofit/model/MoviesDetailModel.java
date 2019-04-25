package Example.com.moviedbusingmvpretrofit.model;

import Example.com.moviedbusingmvpretrofit.movie_detail.MovieDetailsContract;
import Example.com.moviedbusingmvpretrofit.network.APIClient;
import Example.com.moviedbusingmvpretrofit.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Example.com.moviedbusingmvpretrofit.network.APIClient.API_KEY;
import static Example.com.moviedbusingmvpretrofit.utilities.Constants.CREDITS;

public class MoviesDetailModel implements MovieDetailsContract.Model {

    private static final String TAG = "MoviesDetailsModel";

    @Override
    public void getMovieDetails(final OnFinishedListener onFinishedListener, int movie_id) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<Movie> call = apiInterface.getMovieDetails(movie_id, API_KEY, CREDITS);
        //Call<Movie> call = apiInterface.getMovieDetails(movie_id,API_KEY,CREDITS);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                onFinishedListener.onFinished(movie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                onFinishedListener.onFailure(t);
            }
        });
    }
}
