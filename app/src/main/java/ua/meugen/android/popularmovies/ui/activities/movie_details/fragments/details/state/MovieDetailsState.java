package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state;

import java.util.UUID;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * @author meugen
 */

public interface MovieDetailsState extends MvpState {

    String PARAM_MOVIE_ID = "movieId";

    int getMovieId();

    void setMovieId(int movieId);
}
