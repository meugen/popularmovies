package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.NewTokenDto;

public class NewTokenLoader extends AbstractLoader<NewTokenDto> {

    @Inject
    public NewTokenLoader(final Context context, final Api api) {
        super(context, api);
    }

    @Override
    protected NewTokenDto loadInBackground(final Api api) throws IOException {
        return api.newToken();
    }
}
