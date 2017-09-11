package ua.meugen.android.popularmovies.app.utils;

import com.pushtorefresh.storio.operations.PreparedOperation;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author meugen
 */

public class RxUtils {

    private RxUtils() {}

    public static <T> Single<T> asSingle(final PreparedOperation<T> operation) {
        return Single.create(emitter -> {
            T result = operation.executeAsBlocking();
            if (!emitter.isDisposed()) {
                emitter.onSuccess(result);
            }
        });
    }

    public static <T> Observable<T> asObservable(final PreparedOperation<T> operation) {
        return asSingle(operation).toObservable();
    }

    public static <T> Completable asCompletable(final PreparedOperation<T> operation) {
        return Completable.create(emitter -> {
            operation.executeAsBlocking();
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }
}
