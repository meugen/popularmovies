package ua.meugen.android.popularmovies.app.services.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by meugen on 30.11.2017.
 */

public class SyncService extends Service {

    @Inject SyncAdapter adapter;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return adapter.getSyncAdapterBinder();
    }
}
