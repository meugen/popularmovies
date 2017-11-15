package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.meugen.android.popularmovies.model.api.AppActionApi;

/**
 * Created by meugen on 15.11.2017.
 */

abstract class OfflineFirstActionApi<Req, Resp> extends BaseActionApi implements AppActionApi<Req, Resp> {

    private ObservableEmitter<Resp> emitter;

    @Override
    public final Observable<Resp> action(final Req req) {
        return offlineData(req)
                .flatMapObservable(resp -> requestApi(req, resp));
    }

    @Override
    final void clear() {
        super.clear();
        emitter = null;
    }

    private Observable<Resp> requestApi(final Req req, final Resp resp) {
        return Observable.<Resp>create(e -> {
            emitter = e;
            emitter.onNext(resp);
            startNetworkRequest(req);
        }).doOnDispose(this::clear);
    }

    private void startNetworkRequest(final Req req) {
        final Single<Resp> single = networkData(req);
        if (single == null) {
            return;
        }
        final Disposable disposable = single
                .flatMap(resp -> _storeOffline(req, resp))
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

    private Single<Resp> _storeOffline(final Req req, final Resp resp) {
        storeOffline(req, resp);
        return Single.just(resp);
    }

    @NonNull
    abstract Single<Resp> offlineData(final Req req);

    @Nullable
    abstract Single<Resp> networkData(final Req req);

    abstract void storeOffline(final Req req, final Resp resp);
}
