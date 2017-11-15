package ua.meugen.android.popularmovies.model.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ua.meugen.android.popularmovies.app.di.modules.AppModule;

@Singleton
public class SessionStorageImpl implements SessionStorage {

    private static final String PREF_SESSION_ID = "sessionId";
    private static final String PREF_IS_GUEST = "isGuest";
    private static final String PREF_EXPIRES_AT = "expiresAt";

    private final Context context;

    @Inject
    SessionStorageImpl(@Named(AppModule.APP_CONTEXT) final Context context) {
        this.context = context;
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void storeSession(
            final String sessionId,
            final boolean isGuest,
            final Date expiresAt) {
        getPrefs().edit()
                .putString(PREF_SESSION_ID, sessionId)
                .putBoolean(PREF_IS_GUEST, isGuest)
                .putLong(PREF_EXPIRES_AT, expiresAt.getTime())
                .apply();
    }

    @Nullable
    @Override
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
