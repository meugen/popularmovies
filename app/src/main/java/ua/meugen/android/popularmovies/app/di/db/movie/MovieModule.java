package ua.meugen.android.popularmovies.app.di.db.movie;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */
@Module
public abstract class MovieModule {

    @Binds
    @Singleton
    public abstract PutResolver<MovieItemDto> bindMoviePutResolver(
            final MoviePutResolver resolver);

    @Binds @Singleton
    public abstract GetResolver<MovieItemDto> bindMovieGetResolver(
            final MovieGetResolver resolver);

    @Binds @Singleton
    public abstract DeleteResolver<MovieItemDto> bindMovieDeleteResolver(
            final MovieDeleteResolver resolver);

    @Provides
    @Singleton
    public static SQLiteTypeMapping<MovieItemDto> provideMovieTypeMapping(
            final PutResolver<MovieItemDto> putResolver,
            final GetResolver<MovieItemDto> getResolver,
            final DeleteResolver<MovieItemDto> deleteResolver) {
        return SQLiteTypeMapping.<MovieItemDto>builder()
                .putResolver(putResolver)
                .getResolver(getResolver)
                .deleteResolver(deleteResolver)
                .build();
    }
}
