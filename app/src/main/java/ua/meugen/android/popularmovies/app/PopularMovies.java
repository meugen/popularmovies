package ua.meugen.android.popularmovies.app;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.app.di.DaggerAppComponent;
import ua.meugen.android.popularmovies.model.cache.Cache;


public class PopularMovies extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject Cache cache;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        DaggerAppComponent.builder().create(this).inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public void onTrimMemory(final int level) {
        super.onTrimMemory(level);
        if (level >= TRIM_MEMORY_RUNNING_CRITICAL) {
            cache.clear();
        }
    }
}
