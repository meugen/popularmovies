package ua.meugen.android.popularmovies.viewmodel;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Observable;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;

public class AuthorizeViewModel extends Observable {

    private static final String TAG = AuthorizeViewModel.class.getSimpleName();

    public static final Integer ACTION_ERROR = 1;
    public static final Integer ACTION_TOKEN = 2;

    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_ALLOWED = "allowed";

    private static final String BASE_AUTH_URL = "https://www.themoviedb.org/authenticate/";

    private final ModelApi modelApi;
    private final CompositeSubscription compositeSubscription;

    private String token;
    private boolean allowed = false;

    @Inject
    public AuthorizeViewModel(final ModelApi modelApi) {
        this.modelApi = modelApi;

        compositeSubscription = new CompositeSubscription();
    }

    public void loadAuthUrl(final WebView webView) {
        webView.loadUrl(BASE_AUTH_URL + token);
    }

    public void setupWebView(final WebView webView) {
        webView.setWebViewClient(new AuthorizeWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public void restoreInstanceState(final Bundle state) {
        if (state == null) {
            return;
        }
        token = state.getString(PARAM_TOKEN);
        allowed = state.getBoolean(PARAM_ALLOWED);
    }

    public void saveInstanceState(final Bundle outState) {
        outState.putString(PARAM_TOKEN, token);
        outState.putBoolean(PARAM_ALLOWED, allowed);
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
        setChanged();
        if (dto.isSuccess()) {
            this.token = dto.getToken();
            notifyObservers(ACTION_TOKEN);
        } else {
            notifyObservers(dto);
        }
    }

    private void onError(final Throwable th) {
        Log.e(TAG, th.getMessage(), th);

        setChanged();
        notifyObservers(ACTION_ERROR);
    }

    private void gotAllowed() {
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
            setChanged();
            notifyObservers(dto.getSessionId());
        } else {
            setChanged();
            notifyObservers(dto);
        }
    }

    public void reset() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    private class AuthorizeWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(final WebView view, final String url) {
            super.onPageFinished(view, url);
            final Uri uri = Uri.parse(url);
            if ("allow".equals(uri.getLastPathSegment())) {
                gotAllowed();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            return overrideUrl(view, Uri.parse(url));
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(
                final WebView view, final WebResourceRequest request) {
            return overrideUrl(view, request.getUrl());
        }

        private boolean overrideUrl(final WebView webView, final Uri uri) {
            webView.loadUrl(uri.toString());
            return true;
        }
    }
}
