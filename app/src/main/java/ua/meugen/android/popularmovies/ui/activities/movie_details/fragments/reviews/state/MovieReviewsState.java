package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * Created by meugen on 11.09.17.
 */

public interface MovieReviewsState extends MvpState {

    int getMovieId();

    void setMovieId(int movieId);
}
