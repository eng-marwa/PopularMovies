package eng.marwatalaat.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import eng.marwatalaat.popularmovies.adapter.MovieAdapter;
import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.utility.NetworkUtil;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements MovieAdapter.CallBack {
    public static boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.container) != null) {
            mTwoPane = true;

        } else {
            mTwoPane = false;

        }
        if(NetworkUtil.isNetworkAvailable(this) && mTwoPane){
            viewFirstMovie();

        }

    }

    public void viewFirstMovie() {
        Log.i("B","B");
        SharedPreferences firstMoviePref = getSharedPreferences("firstMovie", MODE_PRIVATE);
        if (firstMoviePref != null) {
            Gson gson = new Gson();
            String json = firstMoviePref.getString("firstMovie",gson.toJson(new Movie("","","","","")));
            Movie movie = gson.fromJson(json, Movie.class);
            Log.i("mov",movie.getId()+" "+movie.getTitle()+" "+movie.getOverview()+" "+movie.getPosterPath()+" "+movie.getReleaseDate()+" "+movie.getVoteAverage());

            DetailsFragment detailsFragment = DetailsFragment.getInstance(movie);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
        }


    }

    public void saveFirstMovieInPref(Movie movie) {

        SharedPreferences firstMoviePref = getSharedPreferences("firstMovie", MODE_PRIVATE);
        SharedPreferences.Editor movieEditor = firstMoviePref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movie);
        movieEditor.putString("firstMovie", json).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().hasExtra("movie")) {
            if (mTwoPane) {
                Movie movie = getIntent().getParcelableExtra("movie");
                DetailsFragment detailsFragment = DetailsFragment.getInstance(movie);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();

            }
        }
    }

    @Override
    public void viewSelectedMovie(Movie movie) {

        if (mTwoPane) {
            DetailsFragment detailsFragment = DetailsFragment.getInstance(movie);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
        } else {


            Intent i = new Intent(MainActivity.this, DetailsActivity.class);
            i.putExtra("movie", movie);
            startActivity(i);

        }
        saveFirstMovieInPref(movie);

    }


    public void setScrollPosition(int adapterPosition) {
        SharedPreferences firstMoviePref = getSharedPreferences("firstMovie", MODE_PRIVATE);
        SharedPreferences.Editor movieEditor = firstMoviePref.edit();
        movieEditor.putInt("position",adapterPosition).commit();

    }
    public int getScrollPosition() {
        SharedPreferences firstMoviePref = getSharedPreferences("firstMovie", MODE_PRIVATE);
        return  firstMoviePref.getInt("position", 0);

    }
}
