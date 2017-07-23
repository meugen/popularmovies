package ua.meugen.android.popularmovies.model.injections;

import dagger.Subcomponent;
import ua.meugen.android.popularmovies.loaders.MovieReviewsLoader;
import ua.meugen.android.popularmovies.loaders.MovieVideosLoader;
import ua.meugen.android.popularmovies.loaders.MoviesFavoritesLoader;
import ua.meugen.android.popularmovies.loaders.NewGuestSessionLoader;
import ua.meugen.android.popularmovies.loaders.NewSessionLoader;
import ua.meugen.android.popularmovies.loaders.NewTokenLoader;
import ua.meugen.android.popularmovies.loaders.RateMovieLoader;

@Subcomponent
public interface LoadersComponent {

    MovieReviewsLoader movieReviewsLoader();

    MoviesFavoritesLoader moviesFavoritesLoader();

    MovieVideosLoader movieVideosLoader();

    NewGuestSessionLoader newGuestSessionLoader();

    NewSessionLoader newSessionLoader();

    NewTokenLoader newTokenLoader();

    RateMovieLoader rateMovieLoader();
}
