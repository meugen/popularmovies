package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.OldApi;
import ua.meugen.android.popularmovies.model.dto.NewTokenDto;

public class NewTokenLoader extends AbstractLoader<NewTokenDto> {

    @Inject
    public NewTokenLoader(final Context context, final OldApi api) {
        super(context, api);
    }

    @Override
    protected NewTokenDto loadInBackground(final OldApi api) throws IOException {
        return api.newToken();
    }
}
