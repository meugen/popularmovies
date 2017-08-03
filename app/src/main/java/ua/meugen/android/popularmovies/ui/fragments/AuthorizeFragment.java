package ua.meugen.android.popularmovies.ui.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.model.responses.BaseResponse;
import ua.meugen.android.popularmovies.presenter.AuthorizePresenter;
import ua.meugen.android.popularmovies.ui.AuthorizeView;

public class AuthorizeFragment extends MvpFragment<AuthorizeView, AuthorizePresenter>
        implements AuthorizeView {

    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_ALLOWED = "allowed";

    private static final String BASE_AUTH_URL = "https://www.themoviedb.org/authenticate/";

    @BindView(R.id.webview) WebView webView;

    private OnAuthorizeResultListener resultListener;

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        resultListener = (OnAuthorizeResultListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resultListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_authorize, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.setWebViewClient(new AuthorizeWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setToken(savedInstanceState.getString(PARAM_TOKEN));
            presenter.setAllowed(savedInstanceState.getBoolean(PARAM_ALLOWED));
        }
        presenter.load();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARAM_TOKEN, presenter.getToken());
        outState.putBoolean(PARAM_ALLOWED, presenter.isAllowed());
    }

    @Override
    @NonNull
    public AuthorizePresenter createPresenter() {
        return PopularMovies.appComponent(getContext()).createAuthorizePresenter();
    }

    @Override
    public void gotToken(final String token) {
        webView.loadUrl(BASE_AUTH_URL + token);
    }

    @Override
    public void gotServerError(final BaseResponse response) {
        resultListener.onServerError(response.getStatusMessage(), response.getStatusCode());
    }

    @Override
    public void gotError() {
        resultListener.onError();
    }

    @Override
    public void gotSession(final String sessionId) {
        resultListener.onSuccess(sessionId);
    }

    private class AuthorizeWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(final WebView view, final String url) {
            super.onPageFinished(view, url);
            final Uri uri = Uri.parse(url);
            if ("allow".equals(uri.getLastPathSegment())) {
                presenter.gotAllowed();
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

    public interface OnAuthorizeResultListener {

        void onSuccess(String session);

        void onServerError(String message, int code);

        void onError();
    }
}
