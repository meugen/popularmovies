package ua.meugen.android.popularmovies.ui.activities.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.MvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpViewState;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;

/**
 * @author meugen
 */

public class BaseFragment<S extends MvpState, P extends MvpPresenter<S>> extends Fragment
        implements MvpView {

    @Inject protected P presenter;
    @Inject protected S state;

    @Override
    public void onAttach(final Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            ((MvpViewState) state).attachBundle(getArguments());
        } else {
            ((MvpViewState) state).attachBundle(savedInstanceState);
        }
        presenter.restoreState(state);
        ((MvpViewState) state).detachBundle();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        ((MvpViewState) state).attachBundle(outState);
        presenter.saveState(state);
        ((MvpViewState) state).detachBundle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clean();
    }
}
