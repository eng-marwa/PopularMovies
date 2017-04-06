package eng.marwatalaat.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import eng.marwatalaat.popularmovies.R;
import eng.marwatalaat.popularmovies.YouTubeActivity;
import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.model.MovieTrailers;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MarwaTalaat on 12/10/2016.
 */
public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.TrailersViewHolder> {
    private List<MovieTrailers> movieTrailers;
    private Context context;
    private Movie movie;
    public MovieTrailersAdapter(List<MovieTrailers> movieTrailers , Movie movie) {
        this.movieTrailers = movieTrailers;
        this.movie = movie;
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.trailers_card, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        MovieTrailers movieTrailers = this.movieTrailers.get(position);
        Log.i("vi","http://img.youtube.com/vi/"+movieTrailers.getSource()+"/default.jpg");
        Picasso.with(context).load("http://img.youtube.com/vi/"+movieTrailers.getSource()+"/hqdefault.jpg").into(holder.trailer_thumb);
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }


    public class TrailersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_thumbinal)
        ImageView trailer_thumb;

        public TrailersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            trailer_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, YouTubeActivity.class);
                    i.putExtra("movie", movie);
                    i.putExtra("trailer",movieTrailers.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }
    }
}
