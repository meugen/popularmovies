package ua.meugen.android.popularmovies.ui.activities.authorize.fragment;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.databinding.FragmentAuthorizeBinding;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.presenter.AuthorizePresenter;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state.AuthorizeState;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.view.AuthorizeView;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;

public class AuthorizeFragment extends BaseFragment<AuthorizeState, AuthorizePresenter>
        implements AuthorizeView {

    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_ALLOWED = "allowed";

    private static final String BASE_AUTH_URL = "https://www.themoviedb.org/authenticate/";

    private FragmentAuthorizeBinding binding;

    @Inject OnAuthorizeResultListener resultListener;

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        binding = FragmentAuthorizeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.webview.setWebViewClient(new AuthorizeWebViewClient());
        binding.webview.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();
    }

    @Override
    public void gotToken(final String token) {
        binding.webview.loadUrl(BASE_AUTH_URL + token);
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
