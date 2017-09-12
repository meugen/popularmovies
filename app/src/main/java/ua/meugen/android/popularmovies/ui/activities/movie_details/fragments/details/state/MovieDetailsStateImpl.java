package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state;

import java.util.UUID;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;
import ua.meugen.android.popularmovies.ui.utils.BundleUtils;

/**
 * @author meugen
 */

public class MovieDetailsStateImpl extends BaseMvpState implements MovieDetailsState {

    private static final String MOVIE_ID = "movieId";
    private static final String LISTENER_UUID = "listenerUUID";

    @Inject
    public MovieDetailsStateImpl() {}

    @Override
    public int getMovieId() {
        return bundle.getInt(MOVIE_ID);
    }

    @Override
    public void setMovieId(final int movieId) {
        bundle.putInt(MOVIE_ID, movieId);
    }

    @Override
    public UUID getListenerUUID() {
        return BundleUtils.getUUID(bundle, LISTENER_UUID);
    }

    @Override
    public void setListenerUUID(final UUID uuid) {
        BundleUtils.putUUID(bundle, LISTENER_UUID, uuid);
    }
}
