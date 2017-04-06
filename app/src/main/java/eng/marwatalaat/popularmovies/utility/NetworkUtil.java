package eng.marwatalaat.popularmovies.utility;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by MarwaTalaat on 12/21/2016.
 */

public class NetworkUtil {
    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
