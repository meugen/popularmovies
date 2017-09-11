package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;

/**
 * @author meugen
 */
@Module(includes = BaseFragmentModule.class)
public abstract class MovieVideosFragmentModule {

    @Binds @PerFragment
    abstract Fragment bindFragment(final MovieVideosFragment fragment);
}
