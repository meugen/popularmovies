package ua.meugen.android.popularmovies.app.di.ints;

import java.util.Date;

import ua.meugen.android.popularmovies.model.Session;

public interface SessionStorage {

    void storeSession(String sessionId, boolean isGuest, Date expiresAt);

    Session getSession();
}
