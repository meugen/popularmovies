package ua.meugen.android.popularmovies.app;

import javax.inject.Singleton;

import dagger.Component;
import ua.meugen.android.popularmovies.services.UpdateService;

/**
 * @author meugen
 */

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {

    void inject(UpdateService service);

    Api api();
}
