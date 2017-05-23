package ua.meugen.android.popularmovies.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.ActivityAuthorizeBinding;
import ua.meugen.android.popularmovies.dto.NewSessionDto;
import ua.meugen.android.popularmovies.dto.NewTokenDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.NewSessionLoader;
import ua.meugen.android.popularmovies.loaders.NewTokenLoader;

public class AuthorizeActivity extends AppCompatActivity {

    public static final int RESULT_SERVER_ERROR = 2;
    public static final int RESULT_NETWORK_ERROR = 3;
    public static final int RESULT_NO_NETWORK = 4;

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CODE = "code";
    public static final String EXTRA_SESSION = "session";

    private static final String BASE_AUTH_URL = "https://www.themoviedb.org/authenticate/";

    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_ALLOWED = "allowed";

    private static final int NEW_TOKEN_LOADER_ID = 1;
    private static final int NEW_SESSION_LOADER_ID = 2;

    private final NewTokenCallbacks tokenCallbacks
            = new NewTokenCallbacks();
    private final NewSessionCallbacks sessionCallbacks
            = new NewSessionCallbacks();

    private ActivityAuthorizeBinding binding;

    private String token;
    private boolean allowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_authorize);
        binding.webview.setWebViewClient(new AuthorizeWebViewClient());
        binding.webview.getSettings().setJavaScriptEnabled(true);

        if (savedInstanceState != null) {
            token = savedInstanceState.getString(PARAM_TOKEN);
            allowed = savedInstanceState.getBoolean(PARAM_ALLOWED);
        }
        if (token == null) {
            getSupportLoaderManager().initLoader(NEW_TOKEN_LOADER_ID,
                    null, tokenCallbacks);
        } else if (allowed) {
            createSession();
        }
    }

    private void createSession() {
        final Bundle params = NewSessionLoader.buildParams(token);
        getSupportLoaderManager().initLoader(NEW_SESSION_LOADER_ID,
                params, sessionCallbacks);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARAM_TOKEN, token);
        outState.putBoolean(PARAM_ALLOWED, allowed);
    }

    private void serverError(final String message, final int code) {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_CODE, code);

        setResult(RESULT_SERVER_ERROR, intent);
        finish();
    }

    private void error(final int result) {
        setResult(result);
        finish();
    }

    private void gotToken(final String token) {
        this.token = token;
        binding.webview.loadUrl(BASE_AUTH_URL + token);
    }

    private void gotAllowed() {
        this.allowed = true;
        createSession();
    }

    private void gotSession(final String session) {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_SESSION, session);

        setResult(RESULT_OK, intent);
        finish();
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

    private class NewTokenCallbacks extends AbstractCallbacks<NewTokenDto> {

        @Override
        protected void onData(final NewTokenDto data) {
            gotToken(data.getToken());
        }

        @Override
        protected void onServerError(final String message, final int code) {
            serverError(message, code);
        }

        @Override
        protected void onNetworkError(final IOException ex) {
            error(RESULT_NETWORK_ERROR);
        }

        @Override
        protected void onNoNetwork() {
            error(RESULT_NO_NETWORK);
        }

        @Override
        public Loader<LoaderResult<NewTokenDto>> onCreateLoader(final int id, final Bundle args) {
            return PopularMovies.component(AuthorizeActivity.this)
                    .loadersComponent().newTokenLoader();
        }
    }

    private class NewSessionCallbacks extends AbstractCallbacks<NewSessionDto> {

        @Override
        protected void onData(final NewSessionDto data) {
            gotSession(data.getSessionId());
        }

        @Override
        protected void onServerError(final String message, final int code) {
            serverError(message, code);
        }

        @Override
        protected void onNetworkError(final IOException ex) {
            error(RESULT_NETWORK_ERROR);
        }

        @Override
        protected void onNoNetwork() {
            error(RESULT_NO_NETWORK);
        }

        @Override
        public Loader<LoaderResult<NewSessionDto>> onCreateLoader(final int id, final Bundle args) {
            final NewSessionLoader loader = PopularMovies.component(AuthorizeActivity.this)
                    .loadersComponent().newSessionLoader();
            loader.attachParams(args);
            return loader;
        }
    }
}
