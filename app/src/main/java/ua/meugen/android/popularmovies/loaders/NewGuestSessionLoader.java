package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.OldApi;
import ua.meugen.android.popularmovies.model.dto.NewGuestSessionDto;

public class NewGuestSessionLoader extends AbstractLoader<NewGuestSessionDto> {

    @Inject
    public NewGuestSessionLoader(final Context context, final OldApi api) {
        super(context, api);
    }

    @Override
    protected NewGuestSessionDto loadInBackground(final OldApi api) throws IOException {
        return api.newGuestSession();
    }
}
