package ua.meugen.android.popularmovies.ui.activities.movies;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.meugen.android.popularmovies.app.di.PerActivity;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.MoviesFragment;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.MoviesFragmentModule;

/**
 * @author meugen
 */
@Module(includes = BaseActivityModule.class)
public abstract class MoviesActivityModule {

    @Binds @PerActivity
    public abstract AppCompatActivity bindAppCompatActivity(final MoviesActivity activity);

    @PerFragment
    @ContributesAndroidInjector(modules = MoviesFragmentModule.class)
    public abstract MoviesFragment contributeMoviesFragment();
}
