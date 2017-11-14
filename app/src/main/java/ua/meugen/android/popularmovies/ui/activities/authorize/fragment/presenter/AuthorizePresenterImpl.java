package ua.meugen.android.popularmovies.ui.activities.authorize.fragment.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.network.resp.NewSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewTokenResponse;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state.AuthorizeState;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.view.AuthorizeView;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;

public class AuthorizePresenterImpl extends BaseMvpPresenter<AuthorizeView, AuthorizeState>
        implements AuthorizePresenter {

    private final ModelApi modelApi;

    private String token;
    private boolean allowed = false;

    @Inject
    public AuthorizePresenterImpl(final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

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
        Disposable disposable = modelApi.createNewToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotToken, this::onError);
        getCompositeDisposable().add(disposable);
    }

    private void gotToken(final NewTokenResponse dto) {
        if (dto.success) {
            this.token = dto.token;
            view.gotToken(token);
        } else {
            view.gotServerError(dto);
        }
    }

    private void onError(final Throwable th) {
        Timber.e(th.getMessage(), th);
        view.gotError();
    }

    public void gotAllowed() {
        this.allowed = true;
        createSession();
    }

    private void createSession() {
        Disposable disposable = modelApi.createNewSession(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotSession, this::onError);
        getCompositeDisposable().add(disposable);
    }

    private void gotSession(final NewSessionResponse dto) {
        if (dto.success) {
            view.gotSession(dto.sessionId);
        } else {
            view.gotServerError(dto);
        }
    }
}
