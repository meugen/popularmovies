package ua.meugen.android.popularmovies.app.di;

import javax.inject.Singleton;

import dagger.Component;
import ua.meugen.android.popularmovies.presenter.AuthorizePresenter;
import ua.meugen.android.popularmovies.presenter.MovieDetailsPresenter;
import ua.meugen.android.popularmovies.presenter.MovieReviewsPresenter;
import ua.meugen.android.popularmovies.presenter.MovieVideosPresenter;
import ua.meugen.android.popularmovies.presenter.MoviesPresenter;
import ua.meugen.android.popularmovies.ui.activities.AuthorizeActivity;
import ua.meugen.android.popularmovies.ui.activities.MoviesActivity;
import ua.meugen.android.popularmovies.ui.fragments.MovieDetailsFragment;
import ua.meugen.android.popularmovies.ui.fragments.MovieReviewsFragment;
import ua.meugen.android.popularmovies.ui.fragments.MovieVideosFragment;

/**
 * @author meugen
 */

@Singleton
@Component(modules = { AppModule.class, ReadablesModule.class,
        FuncsModule.class, ExecutorsModule.class })
public interface AppComponent {

    MoviesPresenter createMoviesPresenter();

    AuthorizePresenter createAuthorizePresenter();

    MovieDetailsPresenter createMovieDetailsPresenter();

    MovieReviewsPresenter createMovieReviewsPresenter();

    MovieVideosPresenter createMovieVideosPresenter();
}
