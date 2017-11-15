package ua.meugen.android.popularmovies.app.di.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.network.interceptors.AuthInterceptor;

/**
 * Created by meugen on 13.11.2017.
 */

@Module
public abstract class ApiModule {

    @Provides @Singleton
    static Interceptor[] provideInterceptors(
            final AuthInterceptor authInterceptor) {
        return new Interceptor[] { authInterceptor };
    }

    @Provides @Singleton
    static OkHttpClient provideHttpClient(final Interceptor[] interceptors) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    @Provides @Singleton
    static ServerApi provideApi(final OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client).baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ServerApi.class);
    }
}
