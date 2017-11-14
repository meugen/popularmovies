package ua.meugen.android.popularmovies.app.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.app.di.modules.AppModule;

/**
 * @author meugen
 */

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent extends AndroidInjector<PopularMovies> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<PopularMovies> {}
}
