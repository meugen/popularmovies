package ua.meugen.android.popularmovies.ui.activities.movies.fragment;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerFragment;
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
}
