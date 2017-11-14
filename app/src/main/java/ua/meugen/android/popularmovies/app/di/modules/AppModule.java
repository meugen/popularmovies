package ua.meugen.android.popularmovies.app.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;
import ua.meugen.android.popularmovies.app.PopularMovies;

/**
 * @author meugen
 */

@Module(includes = { AndroidSupportInjectionModule.class, DbModule.class, ActivitiesModule.class, ApiModule.class })
public abstract class AppModule {

    public static final String APP_CONTEXT = "appContext";

    @Binds @Singleton
    abstract Application bindApplication(final PopularMovies popularMovies);

    @Binds @Named(APP_CONTEXT) @Singleton
    abstract Context bindApplicationContext(final Application application);
}
