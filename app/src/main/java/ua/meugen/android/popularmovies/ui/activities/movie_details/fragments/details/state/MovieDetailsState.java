package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state;

import java.util.UUID;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * @author meugen
 */

public interface MovieDetailsState extends MvpState {

    int getMovieId();

    void setMovieId(int movieId);

    UUID getListenerUUID();

    void setListenerUUID(UUID uuid);
}
