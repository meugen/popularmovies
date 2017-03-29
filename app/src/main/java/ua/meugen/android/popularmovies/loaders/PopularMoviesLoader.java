package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;


public class PopularMoviesLoader extends AbstractLoader<PagedMoviesDto> {

    private static final String PARAM_PAGE = "page";

    private static final int DEFAULT_PAGE = 1;

    private final Bundle params;

    public PopularMoviesLoader(final Context context, final Bundle params) {
        super(context);
        this.params = params;
    }

    @Override
    public PagedMoviesDto loadInBackground(final Api api) throws IOException {
        final int page = params.getInt(PARAM_PAGE, DEFAULT_PAGE);
        return api.popularMovies(page);
    }

    public static class ArgsBuilder {

        private final Bundle args = new Bundle();

        public ArgsBuilder page(final String page) {
            this.args.putString(PARAM_PAGE, page);
            return this;
        }

        public Bundle build() {
            return this.args;
        }
    }
}
