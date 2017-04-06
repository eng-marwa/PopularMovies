package eng.marwatalaat.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;


import eng.marwatalaat.popularmovies.adapter.MovieAdapter;
import eng.marwatalaat.popularmovies.adapter.MovieReviewsAdapter;
import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.model.MovieReviews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static eng.marwatalaat.popularmovies.MainActivity.mTwoPane;


public class ReviewsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);
        List<MovieReviews> movieReviewsList = new ArrayList<>();
        recyclerView.setAdapter(new MovieReviewsAdapter(movieReviewsList));

        movie = getIntent().getParcelableExtra("movie");
        setTitle(movie.getTitle());
        List<MovieReviews> movieReviews =getIntent().getParcelableArrayListExtra("movieReviews");
        setUpRecyclerView(movieReviews);
}

    private void setUpRecyclerView(List< MovieReviews > movieReviews) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MovieReviewsAdapter(movieReviews));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i =null;
        if(item.getItemId()==android.R.id.home){
            if(mTwoPane){
               i = new Intent(ReviewsActivity.this,MainActivity.class);
            }else{
                i = new Intent(ReviewsActivity.this,DetailsActivity.class);

            }
            i.putExtra("movie",movie);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
