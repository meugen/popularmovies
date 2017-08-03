package ua.meugen.android.popularmovies.app.di;

import javax.inject.Singleton;

import dagger.Component;
import ua.meugen.android.popularmovies.presenter.AuthorizePresenter;
import ua.meugen.android.popularmovies.presenter.MovieDetailsPresenter;
import ua.meugen.android.popularmovies.presenter.MovieReviewsPresenter;
import ua.meugen.android.popularmovies.presenter.MovieVideosPresenter;
import ua.meugen.android.popularmovies.presenter.MoviesPresenter;

/**
 * @author meugen
 */

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {

    MoviesPresenter createMoviesPresenter();

    AuthorizePresenter createAuthorizePresenter();

    MovieDetailsPresenter createMovieDetailsPresenter();

    MovieReviewsPresenter createMovieReviewsPresenter();

    MovieVideosPresenter createMovieVideosPresenter();
}
