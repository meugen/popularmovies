package ua.meugen.android.popularmovies.ui.activities.movies;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContentResolverCompat;

import java.util.concurrent.TimeUnit;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivity;

public class MoviesActivity extends BaseActivity {

    private static final String AUTHORITY = "ua.meugen.android.popularmovies";
    private static final String ACCOUNT_TYPE = "ua.meugen.android.popularmovies.datasync";
    private static final String ACCOUNT = "datasync";

    private static final long PERIOD = TimeUnit.DAYS.toSeconds(1);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Account account = createSyncAccount();
        ContentResolver.addPeriodicSync(account,
                AUTHORITY, Bundle.EMPTY, PERIOD);
        ContentResolver.setSyncAutomatically(account, AUTHORITY, true);
    }

    public Account createSyncAccount() {
        final Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        final AccountManager accountManager = AccountManager.get(this);
        accountManager.addAccountExplicitly(newAccount, null, null);
        return newAccount;
    }
}
