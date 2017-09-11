package ua.meugen.android.popularmovies.ui.activities.movies.fragment.state;

import ua.meugen.android.popularmovies.presenter.annotations.SortType;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * @author meugen
 */

public interface MoviesState extends MvpState {

    @SortType
    int getSortType();

    void setSortType(@SortType int sortType);
}
