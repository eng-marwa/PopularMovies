package eng.marwatalaat.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import eng.marwatalaat.popularmovies.adapter.MovieAdapter;
import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.model.MoviesTable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static eng.marwatalaat.popularmovies.MainActivity.mTwoPane;

public class WishListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpRecyclerView(viewWishlist());
    }

    private List<Movie> viewWishlist() {
        Cursor cursor = getContentResolver().query(MoviesTable.CONTENT_URI,null,null,null,null);
        return MoviesTable.getRows(cursor,false);
    }


    private void setUpRecyclerView(List<Movie> movies) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MovieAdapter(movies,"wishlist",WishListActivity.this));

    }

    public void viewSelectedMovie(Movie movie) {
        if(mTwoPane){

            Intent i = new Intent(WishListActivity.this, MainActivity.class);
            i.putExtra("movie",movie);
            startActivity(i);

        }else{

            Intent i = new Intent(WishListActivity.this, DetailsActivity.class);
            i.putExtra("movie",movie);
            startActivity(i);

        }
    }
}
