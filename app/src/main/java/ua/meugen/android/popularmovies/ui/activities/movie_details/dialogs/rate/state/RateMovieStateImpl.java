package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.state;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * Created by meugen on 14.11.2017.
 */

public class RateMovieStateImpl extends BaseMvpState implements RateMovieState {

    @Inject
    RateMovieStateImpl() {}

    @Override
    public float getMovieRate() {
        return bundle.getFloat(PARAM_MOVIE_RATE);
    }

    @Override
    public void setMovieRate(final float value) {
        bundle.putFloat(PARAM_MOVIE_RATE, value);
    }
}
