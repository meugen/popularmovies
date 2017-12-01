package ua.meugen.android.popularmovies.app.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.meugen.android.popularmovies.app.di.PerService;
import ua.meugen.android.popularmovies.app.services.auth.AuthenticatorService;
import ua.meugen.android.popularmovies.app.services.auth.AuthenticatorServiceModule;
import ua.meugen.android.popularmovies.app.services.sync.SyncService;
import ua.meugen.android.popularmovies.app.services.sync.SyncServiceModule;

/**
 * Created by meugen on 30.11.2017.
 */

@Module
public abstract class ServicesModule {

    @ContributesAndroidInjector(modules = SyncServiceModule.class)
    @PerService
    abstract SyncService contributeSyncService();

    @ContributesAndroidInjector(modules = AuthenticatorServiceModule.class)
    @PerService
    abstract AuthenticatorService contributeAuthenticatorService();
}
