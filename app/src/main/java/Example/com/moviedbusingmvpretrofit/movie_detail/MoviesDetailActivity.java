package Example.com.moviedbusingmvpretrofit.movie_detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import Example.com.moviedbusingmvpretrofit.R;
import Example.com.moviedbusingmvpretrofit.adapter.CastAdapter;
import Example.com.moviedbusingmvpretrofit.model.Cast;
import Example.com.moviedbusingmvpretrofit.model.Movie;
import Example.com.moviedbusingmvpretrofit.network.APIClient;
import Example.com.moviedbusingmvpretrofit.presenter.MoviesDetailPresenter;

import static Example.com.moviedbusingmvpretrofit.utilities.Constants.KEY_MOVIE_ID;

public class MoviesDetailActivity extends AppCompatActivity implements MovieDetailsContract.View {

    private ImageView ivBackdrop;
    private ProgressBar pbLoadBackdrop;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieRatings;
    private TextView tvOverview;
    private CastAdapter castAdapter;
    private List<Cast> castList;
    private ProgressBar pbLoadCast;
    private TextView tvHomepageValue;
    private TextView tvTaglineValue;
    private TextView tvRuntimeValue;

    private String movieName;

    private MoviesDetailPresenter movieDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initCollapsingToolbar();

        initUI();

        Intent mIntent = getIntent();
        int movieId = mIntent.getIntExtra(KEY_MOVIE_ID, 0);

        movieDetailsPresenter = new MoviesDetailPresenter(this);
        movieDetailsPresenter.requestMovieData(movieId);

    }

    /**
     * Initializing UI components
     */
    private void initUI() {

        ivBackdrop = findViewById(R.id.iv_backdrop);
        pbLoadBackdrop = findViewById(R.id.pb_load_backdrop);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_release_date);
        tvMovieRatings = findViewById(R.id.tv_movie_ratings);
        tvOverview = findViewById(R.id.tv_movie_overview);

        castList = new ArrayList<>();
        RecyclerView rvCast = findViewById(R.id.rv_cast);
        castAdapter = new CastAdapter(this, castList);
        rvCast.setAdapter(castAdapter);
        pbLoadCast = findViewById(R.id.pb_cast_loading);

        tvHomepageValue = findViewById(R.id.tv_homepage_value);
        tvTaglineValue = findViewById(R.id.tv_tagline_value);
        tvRuntimeValue = findViewById(R.id.tv_runtime_value);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(movieName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void showProgress() {
        pbLoadBackdrop.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoadCast.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(Movie movie) {

        if (movie != null) {

            movieName = movie.getTitle();
            tvMovieTitle.setText(movie.getTitle());
            tvMovieReleaseDate.setText(movie.getReleaseDate());
            tvMovieRatings.setText(String.valueOf(movie.getRating()));
            tvOverview.setText(movie.getOverview());

            // loading album cover using Glide library
            Glide.with(this)
                    .load(APIClient.BACKDROP_BASE_URL + movie.getBackdropPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                    .into(ivBackdrop);

            castList.clear();
            castList.addAll(movie.getCredits().getCast());
            castAdapter.notifyDataSetChanged();

            tvTaglineValue.setText(movie.getTagline() != null ? movie.getTagline() : "N/A");
            tvHomepageValue.setText(movie.getHomepage() != null ? movie.getHomepage() : "N/A");
            tvRuntimeValue.setText(movie.getRunTime() != null ? movie.getRunTime() : "N/A");
        }

    }

    @Override
    public void onResponseFailure(Throwable throwable) {


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieDetailsPresenter.onDestroy();
    }
}
