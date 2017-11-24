package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.cache.Cache;

/**
 * Created by meugen on 15.11.2017.
 */

abstract class OfflineFirstActionApi<Req, Resp> extends BaseActionApi implements AppActionApi<Req, Resp> {

    @Inject Cache cache;

    private ObservableEmitter<Resp> emitter;

    @Override
    public final Observable<Resp> action(final Req req) {
        final CachedReq<Req> cachedReq = new CachedReq<>(req, cacheKey(req));
        final Observable<Resp> cachedData = retrieveCache(cachedReq.key);
        final Observable<Resp> data = offlineData(req)
                .flatMapObservable(resp -> requestApi(cachedReq, resp));
        return Observable.amb(Arrays.asList(cachedData, data));
    }

    @Override
    final void clear() {
        super.clear();
        emitter = null;
    }

    private Observable<Resp> retrieveCache(final String key) {
        return Observable.create(e -> {
            final Resp resp = cache.get(key);
            if (resp != null) {
                e.onNext(resp);
            }
        });
    }

    private Observable<Resp> requestApi(final CachedReq<Req> cachedReq, final Resp resp) {
        return Observable.<Resp>create(e -> {
            emitter = e;
            cache.set(cachedReq.key, resp);
            if (!emitter.isDisposed()) {
                emitter.onNext(resp);
                startNetworkRequest(cachedReq);
            }
        }).doOnDispose(this::clear);
    }

    private void startNetworkRequest(final CachedReq<Req> cachedReq) {
        final Single<Resp> single = networkData(cachedReq.req);
        if (single == null) {
            return;
        }
        final Disposable disposable = single
                .flatMap(resp -> _storeOffline(cachedReq, resp))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNetworkSuccess, this::onNetworkError);
        getCompositeDisposable().add(disposable);
    }

    private void onNetworkSuccess(final Resp resp) {
        if (emitter != null && !emitter.isDisposed()) {
            emitter.onNext(resp);
        }
    }

    private void onNetworkError(final Throwable th) {
        if (emitter != null && !emitter.isDisposed()) {
            emitter.onError(th);
        }
    }

    private Single<Resp> _storeOffline(final CachedReq<Req> cachedReq, final Resp resp) {
        cache.set(cachedReq.key, resp);
        storeOffline(cachedReq.req, resp);
        return Single.just(resp);
    }

    @NonNull
    abstract Single<Resp> offlineData(final Req req);

    @Nullable
    abstract Single<Resp> networkData(final Req req);

    abstract void storeOffline(final Req req, final Resp resp);

    @NonNull
    abstract String cacheKey(Req req);
}

class CachedReq<Req> {

    final Req req;
    final String key;

    CachedReq(final Req req, final String key) {
        this.req = req;
        this.key = key;
    }
}
