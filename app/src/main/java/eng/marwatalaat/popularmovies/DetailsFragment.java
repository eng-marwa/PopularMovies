package eng.marwatalaat.popularmovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import eng.marwatalaat.popularmovies.adapter.MovieTrailersAdapter;
import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.model.MovieReviews;
import eng.marwatalaat.popularmovies.model.MovieTrailers;
import eng.marwatalaat.popularmovies.model.ReviewsResponse;
import eng.marwatalaat.popularmovies.model.TrailersResponse;
import eng.marwatalaat.popularmovies.service.MovieClient;
import eng.marwatalaat.popularmovies.service.MovieService;
import eng.marwatalaat.popularmovies.utility.NetworkUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;
import static eng.marwatalaat.popularmovies.MainActivity.mTwoPane;
import static eng.marwatalaat.popularmovies.service.MovieClient.setRetrofitClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    @BindView(R.id.movie_poster)
    ImageView posterImage;

    @BindView(R.id.movie_overview)
    TextView movieOverview;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;

    @BindView(R.id.movie_vote)
    TextView movieVote;

    @BindView(R.id.more)
    TextView readMore;

    @BindView(R.id.first_user)
    TextView first_review_user;

    @BindView(R.id.first_review)
    TextView first_review;

    @BindView(R.id.more_review)
    TextView more_review;

    @BindView(R.id.recyclerView)
    RecyclerView trailersRecyclerView;

    @BindView(R.id.horizontalLine)
    View horizontalLine;

    @BindView(R.id.tr_text)
    TextView textView;

    private Movie movie;
    private List<MovieReviews> movieReviewsList;

    @OnClick(R.id.more)
    public void readMoreAction() {
        if (movie != null) {
            Intent i = new Intent(getActivity(), MovieOverviewActivity.class);
            i.putExtra("movie", movie);
            i.putExtra("pane", mTwoPane);
            startActivity(i);
        }
    }

    @OnClick(R.id.more_review)
    public void moreReviews() {
        Intent i = new Intent(getActivity(), ReviewsActivity.class);
        i.putExtra("movie", movie);
        if (movieReviewsList != null) {
            i.putParcelableArrayListExtra("movieReviews", new ArrayList<MovieReviews>(movieReviewsList));
            startActivity(i);
        }
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment getInstance(Movie movie) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        List<MovieTrailers> movieTrailers = new ArrayList<>();
        trailersRecyclerView.setAdapter(new MovieTrailersAdapter(movieTrailers, movie));
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();
        if (bundle != null) {
            movie = bundle.getParcelable("movie");
            Log.i("what",movie==null?"y":"n");
            movieOverview.setText(movie.getOverview());
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(posterImage);
            movieReleaseDate.setText(movie.getReleaseDate());
            movieVote.setText(movie.getVoteAverage() + "");
            movieTitle.setText(movie.getTitle());
            fetchMovieReviews(movie.getId());
            fetchMovieTrailers(movie.getId());

        }
    }

    private void fetchMovieReviews(String movie_id) {

        Retrofit retrofitClient = MovieClient.setRetrofitClient();
        MovieService movieService = retrofitClient.create(MovieService.class);
        Call<ReviewsResponse> movieReviews = movieService.getMovieReviews(movie_id, getString(R.string.api_key));
        Log.i("req", movieReviews.request().url().toString());
        movieReviews.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                ReviewsResponse reviewsResponse = response.body();
                if(reviewsResponse!=null) {
                    movieReviewsList = reviewsResponse.getResults();
                    setUpReviews(movieReviewsList);
                }

            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (!NetworkUtil.isNetworkAvailable(getActivity())) {
                    first_review_user.setVisibility(View.GONE);
                    first_review.setText("No Internet Connection");
                    first_review.setGravity(Gravity.CENTER);
                    more_review.setVisibility(View.GONE);
                    horizontalLine.setVisibility(View.GONE);

                }
            }
        });

    }

    private void setUpReviews(List<MovieReviews> movieReviewsList) {


        if (!movieReviewsList.isEmpty()) {
            MovieReviews firstReview = movieReviewsList.get(0);
            first_review_user.setText(firstReview.getAuthor());
            first_review.setText(firstReview.getContent());
        } else {
            first_review_user.setVisibility(View.GONE);
            first_review.setText("No Reviews Available");
            first_review.setGravity(Gravity.CENTER);
            more_review.setVisibility(View.GONE);
            horizontalLine.setVisibility(View.GONE);
        }


    }

    private void fetchMovieTrailers(String id) {


        MovieService movieService = setRetrofitClient().create(MovieService.class);
        Call<TrailersResponse> movieTrailers = movieService.getMovieTrailers(id, getString(R.string.api_key));
        Log.i("req", movieTrailers.request().url().toString());
        movieTrailers.enqueue(new Callback<TrailersResponse>() {
            @Override

            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                TrailersResponse trailersResponse = response.body();
                if(trailersResponse!=null) {
                    List<MovieTrailers> movieTrailers = trailersResponse.getYoutube();
                    setUpTrailersRecyclerView(movieTrailers);
                }
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (!NetworkUtil.isNetworkAvailable(getActivity())) {

                    trailersRecyclerView.setVisibility(View.GONE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.conn);

                }
            }
        });
    }


    private void setUpTrailersRecyclerView(List<MovieTrailers> movieTrailers) {
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trailersRecyclerView.setAdapter(new MovieTrailersAdapter(movieTrailers, movie));

    }

}
