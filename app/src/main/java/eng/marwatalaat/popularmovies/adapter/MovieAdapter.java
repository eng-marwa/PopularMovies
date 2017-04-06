package eng.marwatalaat.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import eng.marwatalaat.popularmovies.DetailsActivity;
import eng.marwatalaat.popularmovies.MainActivity;
import eng.marwatalaat.popularmovies.MoviesFragment;
import eng.marwatalaat.popularmovies.R;
import eng.marwatalaat.popularmovies.WishListActivity;
import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.provider.Favoriate;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static eng.marwatalaat.popularmovies.MainActivity.mTwoPane;


/**
 * Created by MarwaTalaat on 12/2/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {


    private List<Movie> movies;
    private Context context;
    private String activityName;

    public MovieAdapter(List<Movie> movies, String activityName , Context context) {
        this.movies = movies;
        this.activityName = activityName;


    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        if (activityName.equals("main")) {
            view = inflater.inflate(R.layout.movie_item, parent, false);
        } else {
            view = inflater.inflate(R.layout.wishlist_card, parent, false);

        }
        MovieHolder holder = new MovieHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, int position) {
        final Movie movie = movies.get(position);
        if (alreadyInWishlist(movie)) {
            holder.favButton.setChecked(true);
        } else {
            holder.favButton.setChecked(false);
        }
        if (activityName.equals("main")) {
            holder.movie_rate.setText(movie.getVoteAverage());

        }
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(holder.movie_poster);
        holder.movie_title.setText(movie.getOriginalTitle());
        holder.movie_release_date.setText(movie.getReleaseDate());
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = ((ToggleButton) view).isChecked();
                if (isChecked) {
                    Toast.makeText(context, Favoriate.addToWishlist(movie, context), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, Favoriate.removeFromWishlist(movie, context), Toast.LENGTH_SHORT).show();

                }

            }
        });
        if (activityName.equals("wishlist")) {
            holder.favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movies.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                    Toast.makeText(context, Favoriate.removeFromWishlist(movie, context), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView movie_poster;

        @BindView(R.id.movie_title)
        TextView movie_title;

        @BindView(R.id.fav)
        ToggleButton favButton;

        @Nullable
        @BindView(R.id.movie_rate)
        TextView movie_rate;


        @BindView(R.id.movie_release_date)
        TextView movie_release_date;

        @BindView(R.id.card_view)
        CardView card_view;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activityName.equals("main")) {
                        ((MainActivity) context).viewSelectedMovie(movies.get(getAdapterPosition()));
                        ((MainActivity) context).setScrollPosition(getAdapterPosition());

                    } else {
                        ((WishListActivity) context).viewSelectedMovie(movies.get(getAdapterPosition()));

                    }


                }

            });


        }
    }

    public boolean alreadyInWishlist(Movie movie) {
        SharedPreferences sh = context.getSharedPreferences("wishlist", Context.MODE_PRIVATE);
        return sh.contains(movie.getId());

    }

    public interface CallBack {
        public void viewSelectedMovie(Movie movie);
    }
}
