package ua.meugen.android.popularmovies.ui.activities.movies.fragment.state;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * @author meugen
 */
public class MoviesStateImpl extends BaseMvpState implements MoviesState {

    private static final String SORT_TYPE = "sortType";

    @Inject
    public MoviesStateImpl() {}

    @SuppressWarnings("ResourceType")
    @Override
    public int getSortType() {
        return bundle.getInt(SORT_TYPE, SortType.POPULAR);
    }

    @Override
    public void setSortType(@SortType final int sortType) {
        bundle.putInt(SORT_TYPE, sortType);
    }
}
