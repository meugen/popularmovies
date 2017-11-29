package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.cache.Cache;

/**
 * Created by meugen on 15.11.2017.
 */

abstract class OfflineFirstActionApi<Req, Resp> extends BaseActionApi implements AppCachedActionApi<Req, Resp> {

    @Override
    public final Observable<Resp> action(final Req req) {
        final Single<Resp> data = offlineData(req)
                .onErrorResumeNext(networkData(req))
                .map(resp -> _storeOffline(req, resp));
        return Single.fromCallable(() -> retrieveCache(req))
                .onErrorResumeNext(data)
                .toObservable();
    }

    private Resp _storeOffline(final Req req, final Resp resp) {
        Resp cachedResp = storeCache(req, resp);
        storeOffline(req, cachedResp);
        return cachedResp;
    }

    @NonNull
    abstract Single<Resp> offlineData(final Req req);

    @NonNull
    abstract Single<Resp> networkData(final Req req);

    abstract void storeOffline(final Req req, final Resp resp);

    @NonNull
    abstract Resp retrieveCache(final Req req);

    abstract Resp storeCache(final Req req, final Resp resp);
}
