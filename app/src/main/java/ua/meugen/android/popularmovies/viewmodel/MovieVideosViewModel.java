package ua.meugen.android.popularmovies.viewmodel;

import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.view.adapters.VideosAdapter;

public class MovieVideosViewModel {

    private static final String PARAM_MOVIE_ID = "movieId";

    public static void bindMovieId(final Bundle args, final int movieId) {
        args.putInt(PARAM_MOVIE_ID, movieId);
    }

    public final VideosAdapter adapter;

    private final ModelApi modelApi;

    private int movieId;

    @Inject
    public MovieVideosViewModel(
            final Context context,
            final ModelApi modelApi) {
        this.modelApi = modelApi;

        this.adapter = new VideosAdapter(context, this);
    }

    public void restoreInstanceState(final Bundle state) {
        movieId = state.getInt(PARAM_MOVIE_ID);
    }

    public void saveInstanceState(final Bundle outState) {
        outState.putInt(PARAM_MOVIE_ID, movieId);
    }

    public void load() {

    }
}
