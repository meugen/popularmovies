package ua.meugen.android.popularmovies.ui.activities.authorize.fragment.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.app.api.ModelApi;
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
    public void onCreate(final AuthorizeState state) {
        super.onCreate(state);
        token = state.getToken();
        allowed = state.isAllowed();
    }

    @Override
    public void onSaveInstanceState(final AuthorizeState state) {
        super.onSaveInstanceState(state);
        state.setToken(token);
        state.setAllowed(allowed);
    }

    @Override
    public void onStart() {
        super.onStart();
        load();
    }

    private void load() {
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

    private void gotToken(final NewTokenDto dto) {
        if (dto.isSuccess()) {
            this.token = dto.getToken();
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

    private void gotSession(final NewSessionDto dto) {
        if (dto.isSuccess()) {
            view.gotSession(dto.getSessionId());
        } else {
            view.gotServerError(dto);
        }
    }
}
