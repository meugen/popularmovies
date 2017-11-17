package ua.meugen.android.popularmovies.ui.rxloader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;

/**
 * Created by meugen on 17.11.2017.
 */

public class LoaderLifecycleHandler implements LifecycleHandler {

    @Inject @Named(BaseActivityModule.ACTIVITY_CONTEXT)
    Context context;
    @Inject LoaderManager manager;

    @Inject
    public LoaderLifecycleHandler() {}

    private <T> ObservableSource<T> load(
            final Observable<T> upstream,
            final int id, final boolean restart) {
        if (restart) {
            manager.destroyLoader(id);
        }
        manager.initLoader(id, Bundle.EMPTY,
                new RxLoaderCallbacks<>(context, upstream));
        RxLoader<T> loader = (RxLoader<T>) manager.getLoader(id);
        return loader.createObservable();
    }

    @Override
    public <T> ObservableTransformer<T, T> load(final int id) {
        return upstream -> load(upstream, id, false);
    }

    @Override
    public <T> ObservableTransformer<T, T> reload(final int id) {
        return upstream -> load(upstream, id, true);
    }

    @Override
    public <T> Observable<T> next(final int id) {
        RxLoader<T> loader = (RxLoader<T>) manager.getLoader(id);
        return loader == null ? null : loader.createObservable();
    }

    @Override
    public boolean hasLoader(final int id) {
        return manager.getLoader(id) != null;
    }

    @Override
    public void clear(final int id) {
        manager.destroyLoader(id);
    }

    private static class RxLoaderCallbacks<T> implements LoaderManager.LoaderCallbacks<T> {

        private final Context context;
        private final Observable<T> observable;

        RxLoaderCallbacks(final Context context, final Observable<T> observable) {
            this.context = context;
            this.observable = observable;
        }

        @Override
        public Loader<T> onCreateLoader(final int id, final Bundle args) {
            return new RxLoader<>(context, observable);
        }

        @Override
        public void onLoadFinished(final Loader<T> loader, final T data) {}

        @Override
        public void onLoaderReset(final Loader<T> loader) {}
    }
}
