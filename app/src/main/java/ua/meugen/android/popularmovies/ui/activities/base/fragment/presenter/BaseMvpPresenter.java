package ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;

/**
 * @author meugen
 */

public abstract class BaseMvpPresenter<V extends MvpView, S extends MvpState>
        implements MvpPresenter<S> {

    @Inject
    protected V view;
    @Inject
    protected S state;

    private CompositeDisposable compositeDisposable;

    protected final CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    @Override
    public void onCreate(final S state) {}

    @Override
    public void onSaveInstanceState(final S state) {}

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}

    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    @Override
    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }
}
