package ua.meugen.android.popularmovies.model.injections;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.api.impl.ModelApiImpl;

/**
 * @author meugen
 */
@Module
public abstract class ModelApiModule {

    @Binds @Singleton
    public abstract ModelApi bindModelApi(final ModelApiImpl impl);
}
