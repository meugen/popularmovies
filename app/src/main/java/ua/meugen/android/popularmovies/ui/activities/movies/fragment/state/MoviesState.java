package ua.meugen.android.popularmovies.ui.activities.movies.fragment.state;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * @author meugen
 */

public interface MoviesState extends MvpState {

    int getPage();

    void setPage(int value);
}
