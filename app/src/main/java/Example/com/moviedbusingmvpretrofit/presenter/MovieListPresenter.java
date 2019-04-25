package Example.com.moviedbusingmvpretrofit.presenter;

import java.util.List;

import Example.com.moviedbusingmvpretrofit.model.Movie;
import Example.com.moviedbusingmvpretrofit.model.MovieListModel;
import Example.com.moviedbusingmvpretrofit.movie_list.MovieListContract;

public class MovieListPresenter implements MovieListContract.Presenter,MovieListContract.Model.OnFinishedListener {

    private MovieListContract.View movieListView;

    private MovieListContract.Model movieListModel;


    public MovieListPresenter(MovieListContract.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new MovieListModel();
    }

    @Override
    public void onFinished(List<Movie> movieArrayList) {

        movieListView.setDataToRecyclerView(movieArrayList);
        if (movieListView != null){
            movieListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        movieListView.onResponseFailure(t);

        if (movieListView != null){
            movieListView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.movieListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {

        if (movieListView != null){
            movieListView.showProgress();
        }

        movieListModel.getMovieList(this,pageNo);

    }

    @Override
    public void requestDataFromServer() {

        if (movieListView != null){
            movieListView.showProgress();
        }

        movieListModel.getMovieList(this,1);
    }
}
