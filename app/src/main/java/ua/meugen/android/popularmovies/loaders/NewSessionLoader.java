package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.OldApi;
import ua.meugen.android.popularmovies.model.dto.NewSessionDto;

public class NewSessionLoader extends AbstractLoader<NewSessionDto> {

    private static final String PARAM_TOKEN = "token";

    public static Bundle buildParams(final String token) {
        final Bundle params = new Bundle();
        params.putString(PARAM_TOKEN, token);
        return params;
    }

    private String token;

    @Inject
    public NewSessionLoader(final Context context, final OldApi api) {
        super(context, api);
    }

    public void attachParams(final Bundle params) {
        this.token = params.getString(PARAM_TOKEN);
    }

    @Override
    protected NewSessionDto loadInBackground(final OldApi api) throws IOException {
        return api.newSession(token);
    }
}
