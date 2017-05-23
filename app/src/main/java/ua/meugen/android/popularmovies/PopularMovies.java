package ua.meugen.android.popularmovies;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.app.AppComponent;
import ua.meugen.android.popularmovies.app.AppModule;
import ua.meugen.android.popularmovies.app.DaggerAppComponent;
import ua.meugen.android.popularmovies.app.LoadersComponent;
import ua.meugen.android.popularmovies.app.Session;


public class PopularMovies extends Application {

    public static final int SORT_TYPE_POPULAR = 1;
    public static final int SORT_TYPE_TOP_RATED = 2;
    public static final int SORT_TYPE_FAVORITES = 3;

    private static final String PREF_SORT_TYPE_INT = "sortTypeInt";
    private static final String PREF_SESSION_ID = "sessionId";
    private static final String PREF_IS_GUEST = "isGuest";
    private static final String PREF_EXPIRES_AT = "expiresAt";

    private AppComponent appComponent;
    private LoadersComponent loadersComponent;

    public static PopularMovies from(final Context context) {
        return (PopularMovies) context.getApplicationContext();
    }

    public static AppComponent appComponent(final Context context) {
        return from(context).getAppComponent();
    }

    public static LoadersComponent loadersComponent(final Context context) {
        return from(context).getLoadersComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public LoadersComponent getLoadersComponent() {
        if (loadersComponent == null) {
            loadersComponent = appComponent.loadersComponent();
        }
        return loadersComponent;
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    public int getSortType() {
        return getPrefs().getInt(PREF_SORT_TYPE_INT, SORT_TYPE_POPULAR);
    }

    public void setSortType(final int sortType) {
        getPrefs().edit()
                .putInt(PREF_SORT_TYPE_INT, sortType)
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
}
