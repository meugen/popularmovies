package ua.meugen.android.popularmovies.ui.activities.authorize.fragment.view;

import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;

public interface AuthorizeView extends MvpView {

    void gotToken(String token);

    void gotServerError(BaseResponse response);

    void gotError();

    void gotSession(String sessionId);
}
