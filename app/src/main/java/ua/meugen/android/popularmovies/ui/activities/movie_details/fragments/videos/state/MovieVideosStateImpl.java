package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * @author meugen
 */

public class MovieVideosStateImpl extends BaseMvpState implements MovieVideosState {

    private static final String MOVIE_ID = "movieId";

    @Inject
    public MovieVideosStateImpl() {}

    @Override
    public int getMovieId() {
        return bundle.getInt(MOVIE_ID);
    }

    @Override
    public void setMovieId(final int movieId) {
        bundle.putInt(MOVIE_ID, movieId);
    }
}
