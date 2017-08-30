package ua.meugen.android.popularmovies.app.di;

import android.content.Context;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.app.executors.MergeMoviesExecutor;
import ua.meugen.android.popularmovies.app.executors.MoviesData;
import ua.meugen.android.popularmovies.app.impls.ModelApiImpl;
import ua.meugen.android.popularmovies.app.impls.SessionStorageImpl;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.presenter.api.ServerApi;
import ua.meugen.android.popularmovies.presenter.helpers.SessionStorage;
import ua.meugen.android.popularmovies.presenter.helpers.TransactionExecutor;

/**
 * @author meugen
 */

@Module
public class AppModule {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private final Context context;

    public AppModule(final Context context) {
        this.context = context;
    }

    @Provides @Singleton
    public OkHttpClient provideHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    @Provides @Singleton
    public Context provideContext() {
        return context;
    }

    @Provides @Singleton
    public ServerApi provideApi(final OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ServerApi.class);
    }

    @Provides @Singleton
    public ModelApi provideModelApi(final ModelApiImpl impl) {
        return impl;
    }

    @Provides @Singleton
    public SessionStorage provideSessionStorage(final SessionStorageImpl impl) {
        return impl;
    }

    @Provides @Singleton
    public Realm provideRealm() {
        final RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        return Realm.getInstance(configuration);
    }

    @Provides @Named("merge-movies") @Singleton
    public TransactionExecutor<MoviesData> bindMergeMoviesExecutor(
            final MergeMoviesExecutor executor) {
        return executor;
    }
}
