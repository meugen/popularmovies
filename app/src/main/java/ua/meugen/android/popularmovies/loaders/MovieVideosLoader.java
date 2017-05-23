package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.VideosDto;

/**
 * @author meugen
 */

public class MovieVideosLoader extends AbstractLoader<VideosDto> {

    private static final String PARAM_ID = "id";

    public static Bundle buildParams(final int id) {
        final Bundle params = new Bundle();
        params.putInt(PARAM_ID, id);
        return params;
    }

    private int id;

    @Inject
    public MovieVideosLoader(final Context context, final Api api) {
        super(context, api);
    }

    public void attachParams(final Bundle params) {
        this.id = params.getInt(PARAM_ID);
    }

    @Override
    protected VideosDto loadInBackground(final Api api) throws IOException {
        return api.movieVideos(this.id);
    }
}
