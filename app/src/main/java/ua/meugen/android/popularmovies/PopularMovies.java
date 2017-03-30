package ua.meugen.android.popularmovies;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ua.meugen.android.popularmovies.app.Api;


public class PopularMovies extends Application {

    private static final String PREF_SORT_TYPE = "sortType";

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

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    public SortType getSortType() {
        final String name = getPrefs().getString(PREF_SORT_TYPE, SortType.POPULAR.name());
        return SortType.valueOf(name);
    }

    public void setSortType(final SortType sortType) {
        getPrefs().edit()
                .putString(PREF_SORT_TYPE, sortType.name())
                .apply();
    }

    public enum SortType {
        POPULAR, TOP_RATED
    }
}
