package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;

public class TopRatedMoviesLoader extends AbstractLoader<PagedMoviesDto> {

    private static final String PARAM_PAGE = "page";

    private static final int DEFAULT_PAGE = 1;

    private final Bundle params;

    public TopRatedMoviesLoader(final Context context, final Bundle params) {
        super(context);
        this.params = params;
    }

    @Override
    protected PagedMoviesDto loadInBackground(final Api api) throws IOException {
        final int page = params.getInt(PARAM_PAGE, DEFAULT_PAGE);
        return api.topRatedMovies(page);
    }
}
