package ua.meugen.android.popularmovies.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.ActivityAuthorizeBinding;
import ua.meugen.android.popularmovies.model.dto.BaseDto;
import ua.meugen.android.popularmovies.viewmodel.AuthorizeViewModel;

public class AuthorizeActivity extends AppCompatActivity implements Observer {

    public static final int RESULT_SERVER_ERROR = 2;
    public static final int RESULT_NETWORK_ERROR = 3;

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CODE = "code";
    public static final String EXTRA_SESSION = "session";

    @Inject AuthorizeViewModel model;

    private ActivityAuthorizeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopularMovies.appComponent(this).inject(this);

        setupBinding();
        model.restoreInstanceState(savedInstanceState);
        model.load();
        model.addObserver(this);
    }

    private void setupBinding() {
        this.binding = DataBindingUtil
                .setContentView(this, R.layout.activity_authorize);
        model.setupWebView(binding.webview);
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
        model.deleteObserver(this);
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

    private void gotSession(final String session) {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_SESSION, session);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void update(final Observable observable, final Object o) {
        if (AuthorizeViewModel.ACTION_ERROR.equals(o)) {
            error(RESULT_NETWORK_ERROR);
        } else if (AuthorizeViewModel.ACTION_TOKEN.equals(o)) {
            model.loadAuthUrl(binding.webview);
        } if (o instanceof BaseDto) {
            BaseDto baseDto = (BaseDto) o;
            serverError(baseDto.getStatusMessage(), baseDto.getStatusCode());
        } else if (o instanceof String) {
            gotSession((String) o);
        }
    }
}
