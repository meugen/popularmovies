package ua.meugen.android.popularmovies;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ua.meugen.android.popularmovies.app.Api;


public class PopularMovies extends Application {

    private Api api;

    public static PopularMovies from(final Context context) {
        return (PopularMovies) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void ensureApi() {
        if (this.api == null) {
            synchronized (this) {
                if (this.api == null) {
                    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    if (BuildConfig.DEBUG) {
                        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(interceptor);
                    }
                    this.api = new Api(builder.build());
                }
            }
        }
    }

    public Api getApi() {
        ensureApi();
        return api;
    }
}
