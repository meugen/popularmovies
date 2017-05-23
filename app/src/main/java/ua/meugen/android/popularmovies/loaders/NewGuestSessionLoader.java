package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.NewGuestSessionDto;

public class NewGuestSessionLoader extends AbstractLoader<NewGuestSessionDto> {

    @Inject
    public NewGuestSessionLoader(final Context context, final Api api) {
        super(context, api);
    }

    @Override
    protected NewGuestSessionDto loadInBackground(final Api api) throws IOException {
        return api.newGuestSession();
    }
}
