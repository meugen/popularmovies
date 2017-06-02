package ua.meugen.android.popularmovies.injections;

import javax.inject.Singleton;

import dagger.Component;
import ua.meugen.android.popularmovies.services.UpdateService;

/**
 * @author meugen
 */

@Singleton
@Component(modules = { AppModule.class, ReadablesModule.class })
public interface AppComponent {

    void inject(UpdateService service);

    LoadersComponent loadersComponent();
}
