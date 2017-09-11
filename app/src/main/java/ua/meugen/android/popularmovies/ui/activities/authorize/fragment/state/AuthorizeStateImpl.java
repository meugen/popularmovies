package ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * Created by meugen on 11.09.17.
 */

public class AuthorizeStateImpl extends BaseMvpState implements AuthorizeState {

    private static final String TOKEN = "token";
    private static final String ALLOWED = "allowed";

    @Override
    public String getToken() {
        return bundle.getString(TOKEN);
    }

    @Override
    public void setToken(final String token) {
        bundle.putString(TOKEN, token);
    }

    @Override
    public boolean isAllowed() {
        return bundle.getBoolean(ALLOWED, false);
    }

    @Override
    public void setAllowed(final boolean allowed) {
        bundle.putBoolean(ALLOWED, allowed);
    }
}
