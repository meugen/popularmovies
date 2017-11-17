package ua.meugen.android.popularmovies.ui.activities.base.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;
import ua.meugen.android.popularmovies.ui.rxloader.LoaderLifecycleHandler;

/**
 * @author meugen
 */
@Module
public abstract class BaseFragmentModule {

    @Provides @PerFragment
    static LoaderManager provideLoaderManager(final Fragment fragment) {
        return fragment.getLoaderManager();
    }

    @Binds @PerFragment
    abstract LifecycleHandler bindLifecycleHandler(final LoaderLifecycleHandler handler);
}
