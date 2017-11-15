package ua.meugen.android.popularmovies.ui.activities.movies.fragment;

import android.support.v4.app.Fragment;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.actions.MoviesActionApi;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.MergeMoviesExecutor;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters.OnMovieClickListener;
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
    abstract OnMovieClickListener bindMovieClickListener(final MoviesFragment fragment);

    @Binds @PerFragment
    abstract Executor<MoviesData> bindMergeMoviesExecutor(final MergeMoviesExecutor executor);

    @Binds @PerFragment
    abstract AppActionApi<Integer, List<MovieItem>> bindMoviesActionApi(final MoviesActionApi api);
}
