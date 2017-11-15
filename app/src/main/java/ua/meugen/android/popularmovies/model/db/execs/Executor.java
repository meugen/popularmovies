package ua.meugen.android.popularmovies.model.db.execs;

import io.reactivex.Completable;

public interface Executor<D> {

    Completable executeTransaction(D data);
}
