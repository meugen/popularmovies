package ua.meugen.android.popularmovies.loaders;

import android.content.Context;

import java.io.IOException;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;


public class PopularMoviesLoader extends AbstractLoader<PagedMoviesDto> {

    public PopularMoviesLoader(final Context context) {
        super(context);
    }

    @Override
    public PagedMoviesDto loadInBackground(final Api api) throws IOException {
        return api.popularMovies();
    }

}
