package ua.meugen.android.popularmovies.app.services.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by meugen on 30.11.2017.
 */

public class SyncService extends Service {

    private SyncAdapter adapter;

    @Override
    public void onCreate() {
        super.onCreate();
        adapter = new SyncAdapter(this, true);
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return adapter.getSyncAdapterBinder();
    }
}
