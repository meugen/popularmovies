package ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;

/**
 * Presenter that can be started for loading right after start new activity/fragment
 */
public interface LoadPresenter {

    /**
     * Load data right after start new activity/fragment
     * @see BaseFragment#onStart()
     */
    void load();
}
