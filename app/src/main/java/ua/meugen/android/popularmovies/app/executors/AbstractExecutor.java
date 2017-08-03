package ua.meugen.android.popularmovies.app.executors;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import ua.meugen.android.popularmovies.presenter.helpers.TransactionExecutor;

abstract class AbstractExecutor<D> implements TransactionExecutor<D> {

    @Override
    public RealmAsyncTask executeTransactionAsync(final Realm realm, final D data) {
        return realm.executeTransactionAsync(r -> execute(r, data));
    }

    protected abstract void execute(final Realm realm, final D data);
}
