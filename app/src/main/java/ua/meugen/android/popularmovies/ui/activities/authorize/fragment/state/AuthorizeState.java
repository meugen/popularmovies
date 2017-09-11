package ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * Created by meugen on 11.09.17.
 */

public interface AuthorizeState extends MvpState {

    String getToken();

    void setToken(String token);

    boolean isAllowed();

    void setAllowed(boolean allowed);
}
