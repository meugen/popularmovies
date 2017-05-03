package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.NewTokenDto;

public class NewTokenLoader extends AbstractLoader<NewTokenDto> {

    public NewTokenLoader(final Context context) {
        super(context);
    }

    @Override
    protected NewTokenDto loadInBackground(final Api api) throws IOException {
        return api.newToken();
    }
}
