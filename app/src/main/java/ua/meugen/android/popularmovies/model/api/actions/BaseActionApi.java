package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by meugen on 15.11.2017.
 */

class BaseActionApi {

    private CompositeDisposable compositeDisposable;

    final CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            synchronized (this) {
                if (compositeDisposable == null) {
                    compositeDisposable = new CompositeDisposable();
                }
            }
        }
        return compositeDisposable;
    }

    @CallSuper
    void clear() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
