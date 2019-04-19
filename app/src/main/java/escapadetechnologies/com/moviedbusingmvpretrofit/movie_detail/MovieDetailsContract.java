package escapadetechnologies.com.moviedbusingmvpretrofit.movie_detail;

import java.util.List;

import escapadetechnologies.com.moviedbusingmvpretrofit.model.Movie;

public interface MovieDetailsContract {

    interface Model{

        interface OnFinishedListener{
            void onFinished(Movie movie);

            void onFailure(Throwable t);
        }

        void getMovieDetails(OnFinishedListener onFinishedListener,int movie_id);
    }


    interface View{

        void showProgress();

        void hideProgress();

        void setDataToViews(Movie movie);

        void onResponseFailure(Throwable t);
    }

    interface Presenter{

        void onDestroy();

        void requestMovieData(int movieId);


    }
}
