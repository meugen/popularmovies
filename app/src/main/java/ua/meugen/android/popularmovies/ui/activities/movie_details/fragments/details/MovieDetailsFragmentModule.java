package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details;

import android.support.v4.app.Fragment;

import dagger.Module;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;

/**
 * @author meugen
 */
@Module(includes = BaseFragmentModule.class)
public abstract class MovieDetailsFragmentModule {

    public abstract Fragment bindFragment(final MovieDetailsFragment fragment);
}
