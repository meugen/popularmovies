package ua.meugen.android.popularmovies.presenter.helpers;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public interface TransactionExecutor<D> {

    RealmAsyncTask executeTransactionAsync(Realm realm, D data);
}
