package ua.meugen.android.popularmovies.app.services.sync;

import android.app.Service;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.app.di.PerService;
import ua.meugen.android.popularmovies.app.services.BaseServiceModule;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.api.actions.MoviesActionApi;
import ua.meugen.android.popularmovies.model.api.req.MoviesReq;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.MergeMoviesExecutor;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;

/**
 * Created by meugen on 30.11.2017.
 */

@Module(includes = BaseServiceModule.class)
public abstract class SyncServiceModule {

    @Binds @PerService
    abstract Service bindService(final SyncService service);

    @Binds @PerService
    abstract AppCachedActionApi<MoviesReq, List<MovieItem>> bindMoviesActionApi(
            final MoviesActionApi api);

    @Provides @PerService
    static KeyGenerator<Integer> provideMoviesKeyGenerator() {
        return KeyGenerator.forMovies();
    }

    @Binds @PerService
    abstract Executor<MoviesData> bindMergeMoviesExecutor(final MergeMoviesExecutor executor);
}
