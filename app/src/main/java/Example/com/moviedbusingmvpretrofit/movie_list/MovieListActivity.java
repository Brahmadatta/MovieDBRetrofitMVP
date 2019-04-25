package Example.com.moviedbusingmvpretrofit.movie_list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Example.com.moviedbusingmvpretrofit.R;
import Example.com.moviedbusingmvpretrofit.movie_detail.MoviesDetailActivity;
import Example.com.moviedbusingmvpretrofit.adapter.MoviesAdapter;
import Example.com.moviedbusingmvpretrofit.model.Movie;
import Example.com.moviedbusingmvpretrofit.presenter.MovieListPresenter;
import Example.com.moviedbusingmvpretrofit.utilities.GridSpacingItemDecoration;

import static Example.com.moviedbusingmvpretrofit.utilities.Constants.KEY_MOVIE_ID;
import static Example.com.moviedbusingmvpretrofit.utilities.GridSpacingItemDecoration.dpToPx;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.View, ShowEmptyView , MovieItemClickListener {

    private static final String TAG = "MovieListActivity";

    private MovieListPresenter movieListPresenter;

    private RecyclerView recyclerView;

    private List<Movie> movieList;

    private MoviesAdapter moviesAdapter;

    private ProgressBar progressBar;

    private int pageNo = 1;

    //constants to load more
    private int previouTotal = 0;

    private boolean loading = true;

    private int visibleThreshold = 5;

    int firstVisibleItem,visibleItemCount,totalItemCount;
    private GridLayoutManager gridLayoutManager;

    private TextView emptyView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        setListeners();

        movieListPresenter = new MovieListPresenter(this);

        movieListPresenter.requestDataFromServer();

    }



    private void initUI() {

        movieList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        moviesAdapter = new MoviesAdapter(movieList,this);

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(this,10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);


        progressBar = findViewById(R.id.loading);
        emptyView = findViewById(R.id.empty_view);


    }


    private void setListeners() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading){
                    if (totalItemCount > previouTotal){
                        loading = false;
                        previouTotal = totalItemCount;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){
                    movieListPresenter.getMoreData(pageNo);
                    loading = true;
                }


            }
        });

    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<Movie> movieArrayList) {
            movieList.addAll(movieArrayList);
            moviesAdapter.notifyDataSetChanged();

            pageNo++;
    }

    @Override
    public void onResponseFailure(Throwable t) {

        Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyView() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {

        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onMovieItemClick(int position) {

        if (position == -1){
            return;
        }
        Intent detailIntent = new Intent(this, MoviesDetailActivity.class);
        detailIntent.putExtra(KEY_MOVIE_ID,movieList.get(position).getId());
        startActivity(detailIntent);

    }
}
