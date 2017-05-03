package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.NewGuestSessionDto;

public class NewGuestSessionLoader extends AbstractLoader<NewGuestSessionDto> {

    public NewGuestSessionLoader(final Context context) {
        super(context);
    }

    @Override
    protected NewGuestSessionDto loadInBackground(final Api api) throws IOException {
        return api.newGuestSession();
    }
}
