package ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * @author meugen
 */

public interface MvpPresenter<S extends MvpState> {

    void restoreState(S state);

    void saveState(S state);

    void clean();
}
