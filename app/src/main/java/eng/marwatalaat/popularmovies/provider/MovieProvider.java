package eng.marwatalaat.popularmovies.provider;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by MarwaTalaat on 12/19/2016.
 */
@SimpleSQLConfig(
        name = "MoviesProvider",
        authority = "eng.marwatalaat.popularmovies.provider.authority",
        database = "popular_movies.db",
        version = 1)
public class MovieProvider implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}

