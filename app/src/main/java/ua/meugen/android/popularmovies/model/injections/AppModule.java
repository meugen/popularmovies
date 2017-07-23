package ua.meugen.android.popularmovies.model.injections;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.model.api.ServerApi;

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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(ServerApi.class);
    }

    @Provides @Singleton
    public Realm provideRealm() {
        final RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        return Realm.getInstance(configuration);
    }
}
