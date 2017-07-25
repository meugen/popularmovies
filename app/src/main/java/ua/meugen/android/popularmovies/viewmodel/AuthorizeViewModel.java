package ua.meugen.android.popularmovies.viewmodel;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import ua.meugen.android.popularmovies.model.dto.NewTokenDto;

public class AuthorizeViewModel extends Observable {

    private static final String TAG = AuthorizeViewModel.class.getSimpleName();

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
            getToken();
        } else if (allowed) {
            createSession();
        }
    }

    private void getToken() {
        Subscription subscription = modelApi.newToken()
                .map(NewTokenDto::getToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotToken, this::onError);
    }

    private void gotToken(final String token) {
        this.token = token;
    }

    private void onError(final Throwable th) {

    }

    private void createSession() {

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
