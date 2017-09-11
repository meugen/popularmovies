package ua.meugen.android.popularmovies.ui.activities.authorize.fragment.presenter;

import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state.AuthorizeState;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.MvpPresenter;

/**
 * Created by meugen on 11.09.17.
 */

public interface AuthorizePresenter extends MvpPresenter<AuthorizeState> {

    void gotAllowed();
}
