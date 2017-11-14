package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerDialog;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;

/**
 * Created by meugen on 14.11.2017.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class SelectSessionTypeDialogModule {

    @Binds @PerDialog
    abstract Fragment bindFragment(final SelectSessionTypeDialog dialog);
}
