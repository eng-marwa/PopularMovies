package eng.marwatalaat.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import eng.marwatalaat.popularmovies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieOverviewActivity extends AppCompatActivity {
@BindView(R.id.more_overview)
    TextView moreOverview;
    private Movie movie;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        ButterKnife.bind(this);

         Intent  i = getIntent();
        movie = i.getParcelableExtra("movie");
        mTwoPane = i.getExtras().getBoolean("pane");
        moreOverview.setText(movie.getOverview());
        setTitle(movie.getTitle());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent i = null;
            if(mTwoPane){
                 i = new Intent(this,MainActivity.class);

            }else{
                 i = new Intent(this,DetailsActivity.class);

            }
            i.putExtra("movie",movie);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
