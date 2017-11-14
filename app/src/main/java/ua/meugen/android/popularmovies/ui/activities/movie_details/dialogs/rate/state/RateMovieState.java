package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.state;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * Created by meugen on 14.11.2017.
 */

public interface RateMovieState extends MvpState {

    String PARAM_MOVIE_RATE = "movieRate";

    float getMovieRate();

    void setMovieRate(float value);
}
