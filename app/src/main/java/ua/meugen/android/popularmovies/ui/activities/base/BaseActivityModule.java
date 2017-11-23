package ua.meugen.android.popularmovies.ui.activities.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.app.di.PerActivity;

/**
 * @author meugen
 */
@Module
public abstract class BaseActivityModule {

    public static final String ACTIVITY_CONTEXT = "activityContext";

    @Binds @PerActivity
    abstract Activity bindActivity(final AppCompatActivity activity);

    @Binds @Named(ACTIVITY_CONTEXT) @PerActivity
    abstract Context bindContext(final Activity activity);

    @Provides @PerActivity
    static FragmentManager provideFragmentManager(final AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
