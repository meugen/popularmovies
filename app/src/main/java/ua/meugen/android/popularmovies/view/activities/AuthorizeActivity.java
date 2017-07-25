package ua.meugen.android.popularmovies.view.activities;

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

import javax.inject.Inject;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.ActivityAuthorizeBinding;
import ua.meugen.android.popularmovies.model.dto.NewSessionDto;
import ua.meugen.android.popularmovies.model.dto.NewTokenDto;
import ua.meugen.android.popularmovies.viewmodel.AuthorizeViewModel;

public class AuthorizeActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CODE = "code";
    public static final String EXTRA_SESSION = "session";

    private static final String BASE_AUTH_URL = "https://www.themoviedb.org/authenticate/";

    @Inject AuthorizeViewModel model;

    private ActivityAuthorizeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopularMovies.appComponent(this).inject(this);

        setupBinding();
        model.restoreInstanceState(savedInstanceState);
        model.load();
    }

    private void setupBinding() {
        this.binding = DataBindingUtil
                .setContentView(this, R.layout.activity_authorize);
        model.setupWebView(binding.webview);
    }

    private void createSession() {
        final Bundle params = NewSessionLoader.buildParams(token);
        getSupportLoaderManager().initLoader(NEW_SESSION_LOADER_ID,
                params, sessionCallbacks);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        model.saveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.reset();
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
}
