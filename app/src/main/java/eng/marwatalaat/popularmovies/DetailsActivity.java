package eng.marwatalaat.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.provider.Favoriate;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.backdrop)
    ImageView backdropImage;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapse_bar;
    private Movie movie;

    private FloatingActionButton fab;
    private String text;
    private boolean flag;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = "";
                if (flag) {
                    Log.i("l","l");
                    text = Favoriate.removeFromWishlist(movie, DetailsActivity.this);
                    fabNotSelected();
                    flag =false;
                } else {
                    Log.i("lx","lx");
                    text = Favoriate.addToWishlist(movie, DetailsActivity.this);
                    fabSelected();
                    flag =true;
                }
                Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getIntent().hasExtra("movie")) {


            movie = getIntent().getParcelableExtra("movie");

            Picasso.with(this).load("http://image.tmdb.org/t/p/w500" + movie.getBackdropPath()).into(backdropImage);
            collapse_bar.setTitle(movie.getTitle());

            DetailsFragment detailsFragment = DetailsFragment.getInstance(movie);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fab != null) {

            if (alreadyInWishlist(movie)) {
                flag =true;
                fabSelected();
            } else {
                flag = false;
                fabNotSelected();

            }
            fab.setSelected(flag);
        }
    }

    public void fabSelected() {
        fab.setImageResource(R.drawable.favorite_on);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFab)));


    }

    public void fabNotSelected() {
        fab.setImageResource(R.drawable.fav);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));


    }


    public boolean alreadyInWishlist(Movie movie) {
        SharedPreferences sh = getSharedPreferences("wishlist", Context.MODE_PRIVATE);
        return sh.contains(movie.getId());

    }
}
