package ua.meugen.android.popularmovies.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ua.meugen.android.popularmovies.BuildConfig;

/**
 * @author meugen
 */

@Module
public class AppModule {

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
}
