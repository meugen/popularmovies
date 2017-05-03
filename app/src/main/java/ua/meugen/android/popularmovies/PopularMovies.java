package ua.meugen.android.popularmovies;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.app.Session;


public class PopularMovies extends Application {

    private static final String PREF_SORT_TYPE = "sortType";
    private static final String PREF_SESSION_ID = "sessionId";
    private static final String PREF_IS_GUEST = "isGuest";
    private static final String PREF_EXPIRES_AT = "expiresAt";

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

    public void storeSession(
            final String sessionId, final boolean isGuest, final Date expiresAt) {
        getPrefs().edit()
                .putString(PREF_SESSION_ID, sessionId)
                .putBoolean(PREF_IS_GUEST, isGuest)
                .putLong(PREF_EXPIRES_AT, expiresAt.getTime())
                .apply();
    }

    public Session getSession() {
        final SharedPreferences prefs = getPrefs();
        final String sessionId = prefs.getString(PREF_SESSION_ID, null);
        if (sessionId == null) {
            return null;
        }
        if (prefs.getBoolean(PREF_IS_GUEST, true)) {
            if (System.currentTimeMillis() > prefs.getLong(PREF_EXPIRES_AT, 0L)) {
                prefs.edit()
                        .remove(PREF_SESSION_ID)
                        .remove(PREF_IS_GUEST)
                        .remove(PREF_EXPIRES_AT)
                        .apply();
                return null;
            }
            return Session.newGuestSession(sessionId);
        }
        return Session.newSession(sessionId);
    }

    public enum SortType {
        POPULAR, TOP_RATED
    }
}
