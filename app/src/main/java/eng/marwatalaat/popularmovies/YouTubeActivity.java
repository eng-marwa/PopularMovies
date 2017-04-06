package eng.marwatalaat.popularmovies;

import android.content.Intent;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.model.MovieTrailers;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import static eng.marwatalaat.popularmovies.MainActivity.mTwoPane;


public class YouTubeActivity extends AppCompatActivity {


    private MovieTrailers movieTrailer;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = getIntent().getParcelableExtra("movie");
        setTitle(movie.getTitle());

        YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment)getSupportFragmentManager()
                .findFragmentById(R.id.youtube);



        movieTrailer = getIntent().getParcelableExtra("trailer");
        youTubePlayerFragment.initialize(getString(R.string.youtube_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {

                    youTubePlayer.loadVideo(movieTrailer.getSource());
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if(youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(YouTubeActivity.this,0).show();
                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trailers,menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v="+movieTrailer.getSource());
        if(shareActionProvider != null){
            shareActionProvider.setShareIntent(shareIntent);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i =null;
        if(item.getItemId()==android.R.id.home){
            if(mTwoPane){
                i = new Intent(YouTubeActivity.this,MainActivity.class);
            }else{
                i = new Intent(YouTubeActivity.this,DetailsActivity.class);

            }
            i.putExtra("movie",movie);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
