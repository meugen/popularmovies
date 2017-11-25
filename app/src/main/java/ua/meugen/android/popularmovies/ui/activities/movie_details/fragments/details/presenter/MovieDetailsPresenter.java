package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.LoadPresenter;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.MvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.ResumePresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;

/**
 * @author meugen
 */

public interface MovieDetailsPresenter extends MvpPresenter<MovieDetailsState>,
        LoadPresenter, ResumePresenter {

    int MOVIE_LOADER_ID = 1;
    int RATE_MOVIE_LOADER_ID = 2;
    int GUEST_SESSION_LOADER_ID = 3;

    void rateMovie();

    void storeUserSession(final String session);

    void switchFavorites();

    void onMovieRated(final float value);

    void createGuestSession();
}
