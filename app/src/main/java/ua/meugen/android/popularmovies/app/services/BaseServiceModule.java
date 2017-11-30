package ua.meugen.android.popularmovies.app.services;

import android.app.Service;
import android.content.Context;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerService;

/**
 * Created by meugen on 30.11.2017.
 */

@Module
public abstract class BaseServiceModule {

    public static final String SERVICE_CONTEXT = "serviceContext";

    @Binds @PerService
    abstract Context bindContext(@Named(SERVICE_CONTEXT) final Service service);
}
