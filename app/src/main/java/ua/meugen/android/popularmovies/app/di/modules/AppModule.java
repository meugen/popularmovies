package ua.meugen.android.popularmovies.app.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.model.cache.Cache;
import ua.meugen.android.popularmovies.model.cache.InMemoryCache;
import ua.meugen.android.popularmovies.model.config.Config;
import ua.meugen.android.popularmovies.model.config.ConfigImpl;
import ua.meugen.android.popularmovies.model.prefs.PrefsStorage;
import ua.meugen.android.popularmovies.model.prefs.PrefsStorageImpl;

/**
 * @author meugen
 */

@Module(includes = { AndroidSupportInjectionModule.class, DbModule.class,
        ActivitiesModule.class, ApiModule.class, ServicesModule.class })
public abstract class AppModule {

    public static final String APP_CONTEXT = "appContext";

    @Binds @Singleton
    abstract Application bindApplication(final PopularMovies popularMovies);

    @Binds @Named(APP_CONTEXT) @Singleton
    abstract Context bindApplicationContext(final Application application);

    @Provides @Singleton
    static SharedPreferences bindPreferences(@Named(APP_CONTEXT) final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Binds @Singleton
    abstract PrefsStorage bindPrefsStorage(final PrefsStorageImpl impl);

    @Binds @Singleton
    abstract Cache bindCache(final InMemoryCache cache);

    @Binds @Singleton
    abstract Config bindConfig(final ConfigImpl impl);
}
