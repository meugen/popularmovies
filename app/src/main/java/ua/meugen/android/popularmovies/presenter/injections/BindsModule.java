package ua.meugen.android.popularmovies.presenter.injections;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import okhttp3.RequestBody;
import rx.functions.Func1;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.api.impl.ModelApiImpl;
import ua.meugen.android.popularmovies.model.functions.ModelToRequestFunc;
import ua.meugen.android.popularmovies.model.json.JsonWritable;
import ua.meugen.android.popularmovies.presenter.helpers.SessionStorage;
import ua.meugen.android.popularmovies.presenter.helpers.impl.SessionStorageImpl;

/**
 * @author meugen
 */
@Module
public abstract class BindsModule {

    @Binds @Singleton
    public abstract Func1<JsonWritable, RequestBody> bindWritableRequestFunc(
            final ModelToRequestFunc func);

    @Binds @Singleton
    public abstract ModelApi bindModelApi(final ModelApiImpl impl);

    @Binds @Singleton
    public abstract SessionStorage bindSessionStorage(final SessionStorageImpl impl);
}
