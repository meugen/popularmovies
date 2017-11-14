package ua.meugen.android.popularmovies.model.session;

import java.util.Date;

public interface SessionStorage {

    void storeSession(String sessionId, boolean isGuest, Date expiresAt);

    Session getSession();
}
