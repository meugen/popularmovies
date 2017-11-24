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
    public V view;

    private CompositeDisposable compositeDisposable;

    protected final CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    @Override
    public void restoreState(final S state) {}

    @Override
    public void saveState(final S state) {}

    @Override
    public void clean() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }
}
