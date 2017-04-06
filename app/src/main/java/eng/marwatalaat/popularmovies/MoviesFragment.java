package eng.marwatalaat.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import eng.marwatalaat.popularmovies.adapter.MovieAdapter;
import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.model.MovieResponse;
import eng.marwatalaat.popularmovies.service.MovieService;
import eng.marwatalaat.popularmovies.utility.NetworkUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static eng.marwatalaat.popularmovies.MainActivity.mTwoPane;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private String API_KEY = "1962bc00de1584940b4f338dc55d6887";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String sorted_by;
    private SharedPreferences pref, firstMoviePref;

    private boolean isOnline;
    private int currentVisiblePosition;
    private Parcelable recyclestate;

    public MoviesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        if (!NetworkUtil.isNetworkAvailable(getActivity())) {
            isOnline = false;
        } else {
            isOnline = true;
        }
        if (!isOnline) {
            view = inflater.inflate(R.layout.no_con, container, false);
            Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();


        } else {
            view = inflater.inflate(R.layout.fragment_movies, container, false);
            pref = getActivity().getSharedPreferences("sort", MODE_PRIVATE);
            firstMoviePref = getActivity().getSharedPreferences("firstMovie", MODE_PRIVATE);
            ButterKnife.bind(this, view);
            setUpRecyclerView(new ArrayList<Movie>());



        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isOnline) {
            sorted_by = pref.getString("sorted_by", "popular");
            fetchMovies(sorted_by);
            currentVisiblePosition = ((MainActivity) getActivity()).getScrollPosition();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.scrollToPosition(currentVisiblePosition);
                }
            }, 1000);

            ((MainActivity) getActivity()).setScrollPosition(0);

        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        if (isOnline) {
            if (sorted_by.equals("popular")) {
                menu.findItem(R.id.popular).setChecked(true);
            } else {
                menu.findItem(R.id.top_rate).setChecked(true);
            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        item.setChecked(true);
        switch (itemId) {
            case R.id.popular:
                if (isOnline) {
                    pref.edit().putString("sorted_by", "popular").commit();
                    fetchMovies("popular");

                }

                break;
            case R.id.top_rate:
                if (isOnline) {
                    pref.edit().putString("sorted_by", "top_rated").commit();
                    fetchMovies("top_rated");


                }
                break;

            case R.id.fav:
                Intent i = new Intent(getActivity(), WishListActivity.class);
                startActivity(i);
                break;
        }

        return true;
    }


    
    private void setUpRecyclerView(List<Movie> movies) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        if (movies.size() != 0) ((MainActivity)getActivity()).saveFirstMovieInPref(movies.get(0));
        MovieAdapter movieAdapter = new MovieAdapter(movies, "main" , getActivity());
        recyclerView.setAdapter(movieAdapter);




    }



    private void fetchMovies(String sortBy) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieResponse> movies = movieService.getMovies(sortBy, API_KEY);
        Log.i("req", movies.request().url().toString());
        movies.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                List<Movie> movieList = movieResponse.getResults();
                Log.i("res", movieResponse.getResults().toString());
                setUpRecyclerView(movieList);


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}
