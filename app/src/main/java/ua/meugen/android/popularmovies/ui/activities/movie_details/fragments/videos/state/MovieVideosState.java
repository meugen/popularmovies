package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * @author meugen
 */

public interface MovieVideosState extends MvpState {

    int getMovieId();

    void setMovieId(int movieId);
}
