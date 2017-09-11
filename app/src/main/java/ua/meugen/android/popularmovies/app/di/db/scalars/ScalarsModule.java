package ua.meugen.android.popularmovies.app.di.db.scalars;

import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * @author meugen
 */
@Module
public abstract class ScalarsModule {

    @Binds @Singleton
    public abstract GetResolver<Integer> bindsIntegerGetResolver(
            final IntegerGetResolver resolver);
}
