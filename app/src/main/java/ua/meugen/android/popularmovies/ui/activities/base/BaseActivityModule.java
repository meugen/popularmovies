package ua.meugen.android.popularmovies.ui.activities.base;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerActivity;

/**
 * @author meugen
 */
@Module
public abstract class BaseActivityModule {

    public static final String ACTIVITY_CONTEXT = "activityContext";

    @Binds @PerActivity
    public abstract Activity bindActivity(final AppCompatActivity activity);

    @Binds @Named(ACTIVITY_CONTEXT) @PerActivity
    public abstract Context bindContext(final Activity activity);
}
