package escapadetechnologies.com.moviedbusingmvpretrofit.movie_list;

import java.util.List;

import escapadetechnologies.com.moviedbusingmvpretrofit.model.Movie;

public interface MovieListContract {

    interface Model{

        interface OnFinishedListener{
            void onFinished(List<Movie> movieArrayList);

            void onFailure(Throwable t);
        }

        void getMovieList(OnFinishedListener onFinishedListener,int pageNo);

    }

    interface View{

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<Movie> movieArrayList);

        void onResponseFailure(Throwable t);

    }

    interface Presenter{
        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();
    }
}
