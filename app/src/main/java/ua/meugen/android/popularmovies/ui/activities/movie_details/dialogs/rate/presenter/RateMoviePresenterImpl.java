package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.presenter;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.OnMovieRatedListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.state.RateMovieState;

/**
 * Created by meugen on 14.11.2017.
 */

public class RateMoviePresenterImpl extends BaseMvpPresenter<MvpView, RateMovieState>
        implements RateMoviePresenter {

    @Inject OnMovieRatedListener listener;

    private float movieRate;

    @Inject
    RateMoviePresenterImpl() {}

    @Override
    public void restoreState(final RateMovieState state) {
        super.restoreState(state);
        movieRate = state.getMovieRate();
    }

    @Override
    public void saveState(final RateMovieState state) {
        super.saveState(state);
        state.setMovieRate(movieRate);
    }

    @Override
    public float getMovieRate() {
        return movieRate;
    }

    @Override
    public void setMovieRate(final float value) {
        movieRate = value;
    }

    @Override
    public void movieRated(final int numStars, final int maxValue) {
        listener.onMovieRated(movieRate / numStars * maxValue);
    }
}
