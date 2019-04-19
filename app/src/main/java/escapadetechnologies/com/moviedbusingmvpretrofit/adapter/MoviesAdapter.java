package escapadetechnologies.com.moviedbusingmvpretrofit.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import escapadetechnologies.com.moviedbusingmvpretrofit.movie_list.MovieListActivity;
import escapadetechnologies.com.moviedbusingmvpretrofit.R;
import escapadetechnologies.com.moviedbusingmvpretrofit.model.Movie;
import escapadetechnologies.com.moviedbusingmvpretrofit.network.APIClient;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{

    private List<Movie> movieList;
    private MovieListActivity movieListActivity;

    public MoviesAdapter(List<Movie> movieList, MovieListActivity movieListActivity) {
        this.movieList = movieList;
        this.movieListActivity = movieListActivity;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card,viewGroup,false);
        return new MoviesViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder moviesViewHolder, final int i) {

        Movie movie = movieList.get(i);

        moviesViewHolder.movie_title.setText(movie.getTitle());
        moviesViewHolder.release_date.setText(movie.getReleaseDate());
        moviesViewHolder.movie_ratings.setText(String.valueOf(movie.getRating()));


        Glide.with(movieListActivity)
                .load(APIClient.IMAGE_BASE_URL + movie.getThumbPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        moviesViewHolder.load_image.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        moviesViewHolder.load_image.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                .into(moviesViewHolder.movie_thumb);

        moviesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieListActivity.onMovieItemClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder{

        ProgressBar load_image;
        ImageView movie_thumb;
        TextView movie_title,release_date,movie_ratings;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_ratings = itemView.findViewById(R.id.movie_ratings);
            release_date = itemView.findViewById(R.id.release_date);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_thumb = itemView.findViewById(R.id.movie_thumb);
            load_image = itemView.findViewById(R.id.load_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
