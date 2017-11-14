package ua.meugen.android.popularmovies.app.di.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.meugen.android.popularmovies.app.di.PerActivity;
import ua.meugen.android.popularmovies.model.session.SessionStorage;
import ua.meugen.android.popularmovies.model.session.SessionStorageImpl;
import ua.meugen.android.popularmovies.ui.activities.authorize.AuthorizeActivity;
import ua.meugen.android.popularmovies.ui.activities.authorize.AuthorizeActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.MovieDetailsActivity;
import ua.meugen.android.popularmovies.ui.activities.movie_details.MovieDetailsActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movies.MoviesActivity;
import ua.meugen.android.popularmovies.ui.activities.movies.MoviesActivityModule;

/**
 * Created by meugen on 13.11.2017.
 */

@Module
public abstract class ActivitiesModule {

    @Binds
    @Singleton
    abstract SessionStorage bindSessionStorage(final SessionStorageImpl impl);

    @PerActivity
    @ContributesAndroidInjector(modules = MoviesActivityModule.class)
    abstract MoviesActivity contributeMoviesActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = MovieDetailsActivityModule.class)
    abstract MovieDetailsActivity contributeMovieDetailsActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = AuthorizeActivityModule.class)
    abstract AuthorizeActivity contributeAuthorizeActivity();
}
