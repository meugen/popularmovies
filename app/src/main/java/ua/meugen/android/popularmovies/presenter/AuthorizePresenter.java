package ua.meugen.android.popularmovies.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.view.AuthorizeView;

public class AuthorizePresenter implements MvpPresenter<AuthorizeView> {

    private static final String TAG = AuthorizePresenter.class.getSimpleName();

    private final ModelApi modelApi;

    private CompositeSubscription compositeSubscription;
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
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView(final boolean retainInstance) {
        this.view = null;
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
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
        Subscription subscription = modelApi.createNewToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotToken, this::onError);
        compositeSubscription.add(subscription);
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
        Log.e(TAG, th.getMessage(), th);
        view.gotError();
    }

    public void gotAllowed() {
        this.allowed = true;
        createSession();
    }

    private void createSession() {
        Subscription subscription = modelApi.createNewSession(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotSession, this::onError);
        compositeSubscription.add(subscription);
    }

    private void gotSession(final NewSessionDto dto) {
        if (dto.isSuccess()) {
            view.gotSession(dto.getSessionId());
        } else {
            view.gotServerError(dto);
        }
    }

    public void reset() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }
}
