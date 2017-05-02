package ua.meugen.android.popularmovies.app;

import okhttp3.HttpUrl;

/**
 * @author meugen
 */

public abstract class Session {

    public static Session newSession(final String id) {
        return new MainSession(id);
    }

    public static Session newGuestSession(final String id) {
        return new GuestSession(id);
    }

    protected final String id;

    protected Session(final String id) {
        this.id = id;
    }

    public abstract void bindToUrl(final HttpUrl.Builder builder);
}

class MainSession extends Session {

    public MainSession(final String id) {
        super(id);
    }

    @Override
    public void bindToUrl(final HttpUrl.Builder builder) {
        builder.addQueryParameter("session_id", id);
    }
}

class GuestSession extends Session {

    public GuestSession(final String id) {
        super(id);
    }

    @Override
    public void bindToUrl(final HttpUrl.Builder builder) {
        builder.addQueryParameter("guest_session_id", id);
    }
}
