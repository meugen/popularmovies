package ua.meugen.android.popularmovies.model.db.execs;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import ua.meugen.android.popularmovies.model.db.AppDatabase;

/**
 * @author meugen
 */

abstract class AbstractExecutor<D> implements Executor<D> {

    @Inject AppDatabase database;

    @Override
    public final Completable executeTransactionAsync(
            final D data) {
        return Completable.create(emitter -> {
            database.runInTransaction(() -> execute(data));
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    protected abstract void execute(final D data);
}
