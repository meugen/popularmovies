package ua.meugen.android.popularmovies.app.di.ints;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import io.reactivex.Completable;

public interface TransactionExecutor<D> {

    Completable executeTransactionAsync(StorIOSQLite storIOSQLite, D data);
}
