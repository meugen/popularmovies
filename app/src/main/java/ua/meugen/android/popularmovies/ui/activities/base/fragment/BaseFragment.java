package ua.meugen.android.popularmovies.ui.activities.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

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
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            ((MvpViewState) state).attachBundle(getArguments());
        } else {
            ((MvpViewState) state).attachBundle(savedInstanceState);
        }
        presenter.onCreate(state);
        ((MvpViewState) state).detachBundle();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        ((MvpViewState) state).attachBundle(outState);
        presenter.onSaveInstanceState(state);
        ((MvpViewState) state).detachBundle();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}
