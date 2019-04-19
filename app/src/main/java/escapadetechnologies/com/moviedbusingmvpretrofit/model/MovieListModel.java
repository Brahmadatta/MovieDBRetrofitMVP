package escapadetechnologies.com.moviedbusingmvpretrofit.model;

import android.util.Log;

import java.util.List;

import escapadetechnologies.com.moviedbusingmvpretrofit.movie_list.MovieListContract;
import escapadetechnologies.com.moviedbusingmvpretrofit.network.APIClient;
import escapadetechnologies.com.moviedbusingmvpretrofit.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static escapadetechnologies.com.moviedbusingmvpretrofit.network.APIClient.API_KEY;

public class MovieListModel implements MovieListContract.Model {

    private static final String TAG = "MovieListModel";

    @Override
    public void getMovieList(final OnFinishedListener onFinishedListener, int pageNo) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<MovieListResponse> call = apiInterface.getPopularMovies(API_KEY,pageNo);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                List<Movie> movies = response.body().getResults();
                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Log.e("errorr",t.getMessage());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
