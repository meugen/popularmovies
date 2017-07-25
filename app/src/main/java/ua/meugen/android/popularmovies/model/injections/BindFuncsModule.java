package ua.meugen.android.popularmovies.model.injections;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import okhttp3.RequestBody;
import rx.functions.Func1;
import ua.meugen.android.popularmovies.model.functions.ModelToRequestFunc;
import ua.meugen.android.popularmovies.model.json.JsonWritable;

/**
 * @author meugen
 */
@Module
public abstract class BindFuncsModule {

    @Binds @Singleton
    public abstract Func1<JsonWritable, RequestBody> bindWritableRequestFunc(
            final ModelToRequestFunc func);
}
