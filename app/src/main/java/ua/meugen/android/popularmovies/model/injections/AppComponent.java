package ua.meugen.android.popularmovies.model.injections;

import javax.inject.Singleton;

import dagger.Component;
import ua.meugen.android.popularmovies.services.UpdateService;
import ua.meugen.android.popularmovies.view.activities.MoviesActivity;
import ua.meugen.android.popularmovies.view.fragments.MovieDetailsFragment;
import ua.meugen.android.popularmovies.view.fragments.MovieReviewsFragment;

/**
 * @author meugen
 */

@Singleton
@Component(modules = { AppModule.class, ReadablesModule.class,
        ProvideFuncsModule.class, BindFuncsModule.class,
        ModelApiModule.class })
public interface AppComponent {

    void inject(UpdateService service);

    void inject(MoviesActivity activity);

    void inject(MovieDetailsFragment fragment);

    void inject(MovieReviewsFragment fragment);

    LoadersComponent loadersComponent();
}
