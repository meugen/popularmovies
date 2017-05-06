package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.app.Session;
import ua.meugen.android.popularmovies.dto.BaseDto;

/**
 * @author meugen
 */

public class RateMovieLoader extends AbstractLoader<BaseDto> {

    private static final String PARAM_ID = "id";
    private static final String PARAM_VALUE = "value";

    public static Bundle buildParams(final int id, final double value) {
        final Bundle params = new Bundle();
        params.putInt(PARAM_ID, id);
        params.putDouble(PARAM_VALUE, value);
        return params;
    }

    private final int id;
    private final double value;

    public RateMovieLoader(final Context context, final Bundle params) {
        super(context);
        this.id = params.getInt(PARAM_ID);
        this.value = params.getDouble(PARAM_VALUE);
    }

    @Override
    protected BaseDto loadInBackground(final Api api) throws IOException {
        final Session session = PopularMovies.from(getContext()).getSession();
        if (session == null) {
            throw new IllegalArgumentException("Session is null");
        }
        return api.rateMovie(session, id, value);
    }
}
