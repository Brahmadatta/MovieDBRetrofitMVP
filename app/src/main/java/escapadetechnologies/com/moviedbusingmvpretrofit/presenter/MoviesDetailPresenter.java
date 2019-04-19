package escapadetechnologies.com.moviedbusingmvpretrofit.presenter;

import escapadetechnologies.com.moviedbusingmvpretrofit.model.Movie;
import escapadetechnologies.com.moviedbusingmvpretrofit.model.MoviesDetailModel;
import escapadetechnologies.com.moviedbusingmvpretrofit.movie_detail.MovieDetailsContract;

public class MoviesDetailPresenter implements MovieDetailsContract.Presenter,MovieDetailsContract.Model.OnFinishedListener{


    private MovieDetailsContract.Model movieDetailmodel;

    private MovieDetailsContract.View moviesDetailView;

    public MoviesDetailPresenter(MovieDetailsContract.View moviesDetailView) {
        this.movieDetailmodel = new MoviesDetailModel();
        this.moviesDetailView = moviesDetailView;
    }

    @Override
    public void onDestroy() {
        moviesDetailView = null;

    }

    @Override
    public void requestMovieData(int movieId) {

        if (moviesDetailView != null){
            moviesDetailView.showProgress();
        }

        movieDetailmodel.getMovieDetails(this,movieId);
    }

    @Override
    public void onFinished(Movie movie) {
        if (moviesDetailView != null){
            moviesDetailView.hideProgress();
        }
        moviesDetailView.setDataToViews(movie);
    }

    @Override
    public void onFailure(Throwable t) {

        if (moviesDetailView != null){
            moviesDetailView.hideProgress();
        }

        moviesDetailView.onResponseFailure(t);
    }
}
