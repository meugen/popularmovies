package ua.meugen.android.popularmovies.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.view.fragments.AuthorizeFragment;

public class AuthorizeActivity extends AppCompatActivity
        implements AuthorizeFragment.OnAuthorizeResultListener {

    public static final int RESULT_SERVER_ERROR = 2;
    public static final int RESULT_NETWORK_ERROR = 3;

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CODE = "code";
    public static final String EXTRA_SESSION = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
    }

    @Override
    public void onSuccess(final String session) {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_SESSION, session);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onServerError(final String message, final int code) {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_CODE, code);

        setResult(RESULT_SERVER_ERROR, intent);
        finish();
    }

    @Override
    public void onError() {
        setResult(RESULT_NETWORK_ERROR);
        finish();
    }
}
