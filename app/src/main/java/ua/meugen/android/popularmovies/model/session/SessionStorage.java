package ua.meugen.android.popularmovies.model.session;

import android.support.annotation.Nullable;

import java.util.Date;

public interface SessionStorage {

    void storeSession(String sessionId, boolean isGuest, Date expiresAt);

    @Nullable
    Session getSession();
}
