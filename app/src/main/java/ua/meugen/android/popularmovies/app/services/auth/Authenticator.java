package ua.meugen.android.popularmovies.app.services.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.app.services.BaseServiceModule;

/**
 * Created by meugen on 30.11.2017.
 */

public class Authenticator extends AbstractAccountAuthenticator {

    @Inject
    Authenticator(
            @Named(BaseServiceModule.SERVICE_CONTEXT) final Context context) {
        super(context);
    }

    @Override
    public Bundle editProperties(
            final AccountAuthenticatorResponse response,
            final String accountType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle addAccount(
            final AccountAuthenticatorResponse response,
            final String accountType,
            final String authAccountType,
            final String[] requiredFeatures,
            final Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle confirmCredentials(
            final AccountAuthenticatorResponse response,
            final Account account,
            final Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(
            final AccountAuthenticatorResponse response,
            final Account account,
            final String authTokenType,
            final Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAuthTokenLabel(final String authTokenType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle updateCredentials(
            final AccountAuthenticatorResponse response,
            final Account account,
            final String authTokenType,
            final Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle hasFeatures(
            final AccountAuthenticatorResponse response,
            final Account account,
            final String[] features) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }
}
