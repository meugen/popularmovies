package ua.meugen.android.popularmovies.ui.activities.authorize.fragment;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;

/**
 * Created by meugen on 11.09.17.
 */
@Module(includes = BaseFragmentModule.class)
public abstract class AuthorizeFragmentModule {

    @Binds @PerFragment
    abstract Fragment bindFragment(final AuthorizeFragment fragment);
}
