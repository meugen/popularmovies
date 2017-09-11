package ua.meugen.android.popularmovies.ui.activities.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerActivity;

/**
 * @author meugen
 */
@Module
public abstract class BaseActivityModule {

    @Binds @PerActivity
    public abstract Activity bindActivity(final AppCompatActivity activity);
}
