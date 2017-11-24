package ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter;

import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.MvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;

/**
 * @author meugen
 */

public interface MoviesPresenter extends MvpPresenter<MoviesState> {

    int LOADER_ID = 1;

    void load();

    void refresh(@SortType int sortType);

    @SortType
    int getSortType();

    void clearCache();
}
