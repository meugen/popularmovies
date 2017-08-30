package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.ui.AuthorizeView;

public class AuthorizePresenter implements MvpPresenter<AuthorizeView> {

    private final ModelApi modelApi;

    private CompositeDisposable compositeDisposable;
    private AuthorizeView view;

    private String token;
    private boolean allowed = false;

    @Inject
    public AuthorizePresenter(final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    @Override
    public void attachView(final AuthorizeView view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView(final boolean retainInstance) {
        this.view = null;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(final boolean allowed) {
        this.allowed = allowed;
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
        compositeDisposable.add(disposable);
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
        compositeDisposable.add(disposable);
    }

    private void gotSession(final NewSessionDto dto) {
        if (dto.isSuccess()) {
            view.gotSession(dto.getSessionId());
        } else {
            view.gotServerError(dto);
        }
    }

    public void reset() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
