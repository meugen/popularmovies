package ua.meugen.android.popularmovies.ui.activities.authorize;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.meugen.android.popularmovies.app.di.PerActivity;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.AuthorizeFragment;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;

/**
 * Created by meugen on 11.09.17.
 */
@Module(includes = BaseActivityModule.class)
public abstract class AuthorizeActivityModule {

    @Binds @PerActivity
    abstract AuthorizeFragment.OnAuthorizeResultListener bindResultListener(final AuthorizeActivity activity);

    @Binds @PerActivity
    abstract AppCompatActivity bindActivity(final AuthorizeActivity activity);

    @PerFragment
    @ContributesAndroidInjector(modules = AuthorizeActivityModule.class)
    abstract AuthorizeFragment contributeAuthorizeFragment();
}
