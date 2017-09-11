package ua.meugen.android.popularmovies.ui.activities.movie_details;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerActivity;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;

/**
 * @author meugen
 */
@Module(includes = BaseActivityModule.class)
public abstract class MovieDetailsActivityModule {

    @Binds @PerActivity
    public abstract AppCompatActivity bindAppCompatActivity(final MovieDetailsActivity activity);
}
