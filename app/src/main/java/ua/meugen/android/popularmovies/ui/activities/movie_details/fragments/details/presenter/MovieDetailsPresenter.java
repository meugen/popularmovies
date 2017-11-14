package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter;

import java.util.UUID;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.MvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;

/**
 * @author meugen
 */

public interface MovieDetailsPresenter extends MvpPresenter<MovieDetailsState> {

    void load();

    void rateMovie();

    void storeUserSession(final String session);

    void switchFavorites();

    void onMovieRated(final float value);

    void createGuestSession();
}
