package ua.meugen.android.popularmovies;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;

import java.util.Date;

import io.realm.Realm;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.injections.AppComponent;
import ua.meugen.android.popularmovies.model.injections.AppModule;
import ua.meugen.android.popularmovies.model.injections.DaggerAppComponent;


public class PopularMovies extends Application {

    @IntDef({SORT_TYPE_POPULAR, SORT_TYPE_TOP_RATED, SORT_TYPE_FAVORITES})
    public @interface SortType {}
    public static final int SORT_TYPE_POPULAR = 1;
    public static final int SORT_TYPE_TOP_RATED = 2;
    public static final int SORT_TYPE_FAVORITES = 3;

    private static final String PREF_SORT_TYPE_INT = "sortTypeInt";
    private static final String PREF_SESSION_ID = "sessionId";
    private static final String PREF_IS_GUEST = "isGuest";
    private static final String PREF_EXPIRES_AT = "expiresAt";

    private AppComponent appComponent;

    public static PopularMovies from(final Context context) {
        return (PopularMovies) context.getApplicationContext();
    }

    public static AppComponent appComponent(final Context context) {
        return from(context).getAppComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    @SortType
    @SuppressWarnings("ResourceType")
    public int getSortType() {
        return getPrefs().getInt(PREF_SORT_TYPE_INT, SORT_TYPE_POPULAR);
    }

    public void setSortType(@SortType final int sortType) {
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
