package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * @author meugen
 */

public class MovieDetailsStateImpl extends BaseMvpState implements MovieDetailsState {

    @Inject
    MovieDetailsStateImpl() {}

    @Override
    public int getMovieId() {
        return bundle.getInt(PARAM_MOVIE_ID);
    }

    @Override
    public void setMovieId(final int movieId) {
        bundle.putInt(PARAM_MOVIE_ID, movieId);
    }
}
