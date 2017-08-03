package ua.meugen.android.popularmovies.ui;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import ua.meugen.android.popularmovies.model.responses.BaseResponse;

public interface AuthorizeView extends MvpView {

    void gotToken(String token);

    void gotServerError(BaseResponse response);

    void gotError();

    void gotSession(String sessionId);
}
