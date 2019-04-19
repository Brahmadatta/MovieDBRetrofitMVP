package escapadetechnologies.com.moviedbusingmvpretrofit.movie_detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import escapadetechnologies.com.moviedbusingmvpretrofit.R;
import escapadetechnologies.com.moviedbusingmvpretrofit.adapter.CastAdapter;
import escapadetechnologies.com.moviedbusingmvpretrofit.model.Cast;
import escapadetechnologies.com.moviedbusingmvpretrofit.model.Movie;
import escapadetechnologies.com.moviedbusingmvpretrofit.network.APIClient;
import escapadetechnologies.com.moviedbusingmvpretrofit.presenter.MoviesDetailPresenter;

import static escapadetechnologies.com.moviedbusingmvpretrofit.utilities.Constants.KEY_MOVIE_ID;

public class MoviesDetailActivity extends AppCompatActivity implements MovieDetailsContract.View{

    private ImageView backdrop;
    private ProgressBar backdropLoad,castLoad;
    private TextView movie_title,releaseDate,movieRatings,overview,homepage_value,tagline_value,runtimeValue;
    private List<Cast> castList;

    private MoviesDetailPresenter moviesDetailPresenter;
    private CastAdapter castAdapter;

    private String movie_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        initCollapsingToolBar();

        initUI();

        Intent intent = getIntent();
        int movieId = intent.getIntExtra(KEY_MOVIE_ID,0);

        moviesDetailPresenter = new MoviesDetailPresenter(this);
        moviesDetailPresenter.requestMovieData(movieId);


    }



    private void initUI() {

        backdrop = findViewById(R.id.backDrop);
        backdropLoad = findViewById(R.id.load_backdrop);
        movie_title = findViewById(R.id.movie_title);
        releaseDate = findViewById(R.id.release_date);
        movieRatings = findViewById(R.id.movie_ratings);
        overview = findViewById(R.id.overView_title);

        castList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView_cast);
        castAdapter = new CastAdapter(this,castList);
        recyclerView.setAdapter(castAdapter);

        castLoad = findViewById(R.id.cast_loading);

        homepage_value = findViewById(R.id.homepage_value);
        tagline_value = findViewById(R.id.tagline_value);
        runtimeValue = findViewById(R.id.runtime_value);

    }

    private void initCollapsingToolBar() {

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(movie_name);
                    isShow = true;
                }else if (isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }

    @Override
    public void showProgress() {
        backdropLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        backdropLoad.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(Movie movie) {

        if (movie != null){

            movie_name = movie.getTitle();
            movie_title.setText(movie.getTitle());
            releaseDate.setText(movie.getReleaseDate());
            movieRatings.setText(String.valueOf(movie.getRating()));
            overview.setText(movie.getOverview());

            Glide.with(this)
                    .load(APIClient.BACKDROP_BASE_URL + movie.getBackdropPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            backdropLoad.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            backdropLoad.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                    .into(backdrop);

            castList.clear();
            castList.addAll(movie.getCredits().getCast());
            castAdapter.notifyDataSetChanged();

            tagline_value.setText(movie.getTagline() != null ? movie.getTagline() : "N/A");
            homepage_value.setText(movie.getHomepage() != null ? movie.getHomepage() : "N/A");
            runtimeValue.setText(movie.getRunTime() != null ? movie.getRunTime() : "N/A");
        }
    }

    @Override
    public void onResponseFailure(Throwable t) {
        Toast.makeText(this, "Errorrr", Toast.LENGTH_SHORT).show();
    }
}
