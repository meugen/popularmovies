package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * Created by meugen on 11.09.17.
 */

public class MovieReviewsStateImpl extends BaseMvpState implements MovieReviewsState {

    private static final String MOVIE_ID = "movieId";

    @Inject
    public MovieReviewsStateImpl() {}

    @Override
    public int getMovieId() {
        return bundle.getInt(MOVIE_ID);
    }

    @Override
    public void setMovieId(final int movieId) {
        bundle.putInt(MOVIE_ID, movieId);
    }
}
