package Example.com.moviedbusingmvpretrofit.movie_detail;

import Example.com.moviedbusingmvpretrofit.model.Movie;

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
