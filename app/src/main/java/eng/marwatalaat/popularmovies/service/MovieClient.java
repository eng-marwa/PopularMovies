package eng.marwatalaat.popularmovies.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MarwaTalaat on 12/6/2016.
 */

public class MovieClient {
    public static Retrofit setRetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
