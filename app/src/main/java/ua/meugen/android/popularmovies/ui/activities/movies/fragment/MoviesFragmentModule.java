package ua.meugen.android.popularmovies.ui.activities.movies.fragment;

import android.support.v4.app.Fragment;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.api.actions.MoviesActionApi;
import ua.meugen.android.popularmovies.model.api.req.MoviesReq;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.MergeMoviesExecutor;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters.OnMoviesListener;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter.MoviesPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter.MoviesPresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesStateImpl;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;

/**
 * @author meugen
 */
@Module(includes = BaseFragmentModule.class)
public abstract class MoviesFragmentModule {

    @Binds @PerFragment
    abstract MoviesPresenter bindMoviesPresenter(final MoviesPresenterImpl impl);

    @Binds @PerFragment
    abstract MoviesState bindMoviesState(final MoviesStateImpl impl);

    @Binds @PerFragment
    abstract MoviesView bindMoviesView(final MoviesFragment fragment);

    @Binds @PerFragment
    abstract Fragment bindFragment(final MoviesFragment fragment);

    @Binds @PerFragment
    abstract OnMoviesListener bindMovieClickListener(final MoviesFragment fragment);

    @Binds @PerFragment
    abstract Executor<MoviesData> bindMergeMoviesExecutor(final MergeMoviesExecutor executor);

    @Binds @PerFragment
    abstract AppCachedActionApi<MoviesReq, List<MovieItem>> bindMoviesActionApi(
            final MoviesActionApi api);

    @Provides @PerFragment
    static KeyGenerator<Integer> provideMoviesKeyGenerator() {
        return KeyGenerator.forMovies();
    }
}
