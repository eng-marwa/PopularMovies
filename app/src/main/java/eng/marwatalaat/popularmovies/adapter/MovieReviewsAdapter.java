package eng.marwatalaat.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eng.marwatalaat.popularmovies.R;
import eng.marwatalaat.popularmovies.model.MovieReviews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MarwaTalaat on 12/10/2016.
 */
public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewsHolder> {
    private List<MovieReviews> movieReviews;


    public MovieReviewsAdapter(List<MovieReviews> movieReviews) {
        this.movieReviews = movieReviews;
    }

    @Override
    public ReviewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_card, parent, false);
        return new ReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsHolder holder, int position) {
        MovieReviews movieReviews = this.movieReviews.get(position);

            holder.autherView.setText(movieReviews.getAuthor());
            holder.contentView.setText(movieReviews.getContent());



    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.first_user)
        TextView autherView;
        @BindView(R.id.first_review)
        TextView contentView;
        @BindView(R.id.more_review)
        TextView moreReviews;
        @BindView(R.id.horizontalLine)
        View horizontalLine;
       public ReviewsHolder(View itemView) {
           super(itemView);
           ButterKnife.bind(this,itemView);
           moreReviews.setVisibility(View.GONE);
           contentView.setEllipsize(null);
           contentView.setMaxLines(Integer.MAX_VALUE);
           horizontalLine.setVisibility(View.GONE);
       }
   }
}
