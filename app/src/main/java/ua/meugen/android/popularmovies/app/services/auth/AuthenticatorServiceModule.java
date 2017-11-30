package ua.meugen.android.popularmovies.app.services.auth;

import android.app.Service;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerService;
import ua.meugen.android.popularmovies.app.services.BaseServiceModule;

/**
 * Created by meugen on 30.11.2017.
 */

@Module(includes = BaseServiceModule.class)
public abstract class AuthenticatorServiceModule {

    @Binds @PerService
    abstract Service bindService(final AuthenticatorService service);
}
