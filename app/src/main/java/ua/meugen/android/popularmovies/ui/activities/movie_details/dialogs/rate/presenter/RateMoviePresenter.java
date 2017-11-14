package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.presenter;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.MvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.state.RateMovieState;

/**
 * Created by meugen on 14.11.2017.
 */

public interface RateMoviePresenter extends MvpPresenter<RateMovieState> {

    float getMovieRate();

    void setMovieRate(float value);

    void movieRated(int numStars, int maxValue);
}
