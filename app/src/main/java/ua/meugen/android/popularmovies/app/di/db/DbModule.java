package ua.meugen.android.popularmovies.app.di.db;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.app.di.db.movie.MovieModule;
import ua.meugen.android.popularmovies.app.di.db.scalars.ScalarsModule;
import ua.meugen.android.popularmovies.app.executors.MergeMoviesExecutor;
import ua.meugen.android.popularmovies.app.executors.MoviesData;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.helpers.TransactionExecutor;

/**
 * @author meugen
 */
@Module(includes = { MovieModule.class, ScalarsModule.class })
public abstract class DbModule {

    @Binds @Singleton
    public abstract TransactionExecutor<MoviesData> bindMoviesTransactionExecutor(
            final MergeMoviesExecutor executor);

    @Provides @Singleton
    public static StorIOSQLite provideStorIOSQLite(
            final DbOpenHelper dbOpenHelper,
            final SQLiteTypeMapping<MovieItemDto> movieMapping) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(dbOpenHelper)
                .addTypeMapping(MovieItemDto.class, movieMapping)
                .build();
    }
}
