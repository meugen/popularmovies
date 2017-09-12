package ua.meugen.android.popularmovies.app.executors;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import io.reactivex.Completable;
import ua.meugen.android.popularmovies.app.di.ints.TransactionExecutor;

/**
 * @author meugen
 */

public abstract class AbstractExecutor<D> implements TransactionExecutor<D> {

    @Override
    public final Completable executeTransactionAsync(
            final StorIOSQLite storIOSQLite, final D data) {
        return Completable.create(source -> {
            final StorIOSQLite.LowLevel lowLevel = storIOSQLite.lowLevel();
            lowLevel.beginTransaction();
            try {
                execute(storIOSQLite, data);
                source.onComplete();
                lowLevel.setTransactionSuccessful();
            } finally {
                lowLevel.endTransaction();
            }
        });
    }

    protected abstract void execute(final StorIOSQLite storIOSQLite, final D data);
}
