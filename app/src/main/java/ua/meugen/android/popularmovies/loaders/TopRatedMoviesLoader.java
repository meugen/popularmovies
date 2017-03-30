package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;

public class TopRatedMoviesLoader extends AbstractLoader<PagedMoviesDto> {

    public TopRatedMoviesLoader(final Context context) {
        super(context);
    }

    @Override
    protected PagedMoviesDto loadInBackground(final Api api) throws IOException {
        return api.topRatedMovies();
    }
}
