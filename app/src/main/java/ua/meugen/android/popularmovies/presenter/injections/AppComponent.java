package ua.meugen.android.popularmovies.presenter.injections;

import javax.inject.Singleton;

import dagger.Component;
import ua.meugen.android.popularmovies.presenter.AuthorizePresenter;
import ua.meugen.android.popularmovies.presenter.MoviesPresenter;
import ua.meugen.android.popularmovies.view.activities.AuthorizeActivity;
import ua.meugen.android.popularmovies.view.activities.MoviesActivity;
import ua.meugen.android.popularmovies.view.fragments.MovieDetailsFragment;
import ua.meugen.android.popularmovies.view.fragments.MovieReviewsFragment;
import ua.meugen.android.popularmovies.view.fragments.MovieVideosFragment;

/**
 * @author meugen
 */

@Singleton
@Component(modules = { AppModule.class, ReadablesModule.class,
        ProvideFuncsModule.class, BindFuncsModule.class,
        ModelApiModule.class })
public interface AppComponent {

    void inject(MoviesActivity activity);

    void inject(MovieDetailsFragment fragment);

    void inject(MovieReviewsFragment fragment);

    void inject(MovieVideosFragment fragment);

    void inject(AuthorizeActivity activity);

    MoviesPresenter createMoviesPresenter();

    AuthorizePresenter createAuthorizePresenter();
}
