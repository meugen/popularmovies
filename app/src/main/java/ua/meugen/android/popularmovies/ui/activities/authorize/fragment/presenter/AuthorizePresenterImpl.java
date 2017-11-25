package ua.meugen.android.popularmovies.ui.activities.authorize.fragment.presenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.network.resp.NewSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewTokenResponse;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state.AuthorizeState;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.view.AuthorizeView;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;

public class AuthorizePresenterImpl extends BaseMvpPresenter<AuthorizeView, AuthorizeState>
        implements AuthorizePresenter {

    private static final int TOKEN_LOADER_ID = 1;
    private static final int SESSION_LOADER_ID = 2;

    @Inject AppActionApi<Void, NewTokenResponse> newTokenActionApi;
    @Inject AppActionApi<String, NewSessionResponse> newSessionActionApi;
    @Inject LifecycleHandler lifecycleHandler;

    private String token;
    private boolean allowed = false;

    @Inject
    AuthorizePresenterImpl() {}

    @Override
    public void restoreState(final AuthorizeState state) {
        super.restoreState(state);
        token = state.getToken();
        allowed = state.isAllowed();
    }

    @Override
    public void saveState(final AuthorizeState state) {
        super.saveState(state);
        state.setToken(token);
        state.setAllowed(allowed);
    }

    public void load() {
        if (token == null) {
            loadToken();
        } else if (allowed) {
            createSession();
        }
    }

    private void loadToken() {
        Disposable disposable = newTokenActionApi
                .action(null)
                .compose(lifecycleHandler.load(TOKEN_LOADER_ID))
                .subscribe(this::onTokenSuccess, this::onTokenError);
        getCompositeDisposable().add(disposable);
    }

    private void onTokenSuccess(final NewTokenResponse response) {
        lifecycleHandler.clear(TOKEN_LOADER_ID);
        if (response.isSuccess()) {
            this.token = response.getToken();
            view.gotToken(token);
        } else {
            view.gotServerError(response);
        }
    }

    private void onTokenError(final Throwable th) {
        onError(th, TOKEN_LOADER_ID);
    }

    private void onError(final Throwable th, final int loaderId) {
        lifecycleHandler.clear(loaderId);
        Timber.e(th.getMessage(), th);
        view.gotError();
    }

    public void gotAllowed() {
        this.allowed = true;
        createSession();
    }

    private void createSession() {
        Disposable disposable = newSessionActionApi
                .action(token)
                .compose(lifecycleHandler.load(SESSION_LOADER_ID))
                .subscribe(this::onSessionSuccess, this::onSessionError);
        getCompositeDisposable().add(disposable);
    }

    private void onSessionSuccess(final NewSessionResponse response) {
        lifecycleHandler.clear(SESSION_LOADER_ID);
        if (response.isSuccess()) {
            view.gotSession(response.getSessionId());
        } else {
            view.gotServerError(response);
        }
    }

    private void onSessionError(final Throwable th) {
        onError(th, SESSION_LOADER_ID);
    }
}
