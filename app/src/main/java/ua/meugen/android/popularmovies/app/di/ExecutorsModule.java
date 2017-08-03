package ua.meugen.android.popularmovies.app.di;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.executors.MergeMoviesExecutor;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.helpers.TransactionExecutor;

@Module
public abstract class ExecutorsModule {

    @Binds @Named("merge-movies") @Singleton
    public abstract TransactionExecutor<List<MovieItemDto>> bindMergeMoviesExecutor(
            final MergeMoviesExecutor executor);
}
