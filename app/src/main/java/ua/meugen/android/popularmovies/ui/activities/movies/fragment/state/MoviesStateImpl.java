package ua.meugen.android.popularmovies.ui.activities.movies.fragment.state;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * @author meugen
 */
public class MoviesStateImpl extends BaseMvpState implements MoviesState {

    private static final String PARAM_PAGE = "page";

    @Inject
    MoviesStateImpl() {}

    @Override
    public int getPage() {
        return bundle.getInt(PARAM_PAGE);
    }

    @Override
    public void setPage(final int value) {
        bundle.putInt(PARAM_PAGE, value);
    }
}
