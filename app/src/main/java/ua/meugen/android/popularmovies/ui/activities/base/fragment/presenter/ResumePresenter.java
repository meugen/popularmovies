package ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;

/**
 * Presenter that can be resumed for loading data that was started before fragment/activity is been recreated (device rotation, etc.)
 */
public interface ResumePresenter {

    /**
     * Resume loading data that was started before fragment/activity is been recreated (device rotation, etc.)
     * @see BaseFragment#onResume()
     */
    void resume();
}
