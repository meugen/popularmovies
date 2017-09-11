package ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * @author meugen
 */

public interface MvpPresenter<S extends MvpState> {

    void onCreate(S state);

    void onSaveInstanceState(S state);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
