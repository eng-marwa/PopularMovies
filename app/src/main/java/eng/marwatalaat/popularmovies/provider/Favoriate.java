package eng.marwatalaat.popularmovies.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import eng.marwatalaat.popularmovies.model.Movie;
import eng.marwatalaat.popularmovies.model.MoviesTable;
import com.google.android.youtube.player.internal.c;
import com.google.android.youtube.player.internal.d;

/**
 * Created by MarwaTalaat on 12/19/2016.
 */

public class Favoriate {



    public static String addToWishlist(Movie movie,Context context){
         context.getContentResolver().insert(MoviesTable.CONTENT_URI, MoviesTable.getContentValues(movie, false));
         addToPreferences(movie.getId(),context);
         return movie.getTitle()+" has beem added";

    }

    private static void addToPreferences(String id ,Context context) {
        SharedPreferences sh = context.getSharedPreferences("wishlist",Context.MODE_PRIVATE);
        sh.edit().putString(id,id).commit();
    }

    public static String removeFromWishlist(Movie movie,Context context){
        int delete = context.getContentResolver().delete(MoviesTable.CONTENT_URI, MoviesTable.FIELD_ID + "=?", new String[]{movie.getId()});
        if(delete >0){
            removeFromPreferences(movie.getId(),context);
        return movie.getTitle()+" has beem removed";}
        else{
         return "Error , Try Again";

        }
    }

    private static void removeFromPreferences(String id ,Context context) {
        SharedPreferences sh = context.getSharedPreferences("wishlist",Context.MODE_PRIVATE);
        sh.edit().remove(id).commit();
    }
}
