package ua.meugen.android.popularmovies.ui.rxloader;

import android.content.Context;
import android.support.v4.content.Loader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * Created by meugen on 17.11.2017.
 */

public class RxLoader<T> extends Loader<T> {

    private final Observable<T> observable;
    private Disposable disposable;
    private ObservableEmitter<T> emitter;
    private T data;
    private Throwable error;

    public RxLoader(final Context context, final Observable<T> observable) {
        super(context);
        this.observable = observable;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        subscribe();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        unsubscribe();
    }

    @Override
    protected void onReset() {
        super.onReset();
        clear();
    }

    public Observable<T> createObservable() {
        return Observable.<T>create(e -> {
            emitter = e;
            if (error != null) {
                e.onError(error);
            } else if (data != null) {
                e.onNext(data);
            }
        }).doOnDispose(this::unsubscribe);
    }

    private void subscribe() {
        if (disposable == null) {
            disposable = observable
                    .doOnDispose(this::clear)
                    .subscribe(this::onDataSuccess, this::onDataError);
        }
    }

    private void unsubscribe() {
        emitter = null;
    }

    private void onDataSuccess(final T data) {
        this.data = data;
        if (emitter != null && !emitter.isDisposed() && error == null) {
            emitter.onNext(data);
        }
    }

    private void onDataError(final Throwable th) {
        this.error = th;
        if (emitter != null && !emitter.isDisposed()) {
            emitter.onError(th);
        }
    }

    private void clear() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = null;
        emitter = null;
        data = null;
        error = null;
    }
}
