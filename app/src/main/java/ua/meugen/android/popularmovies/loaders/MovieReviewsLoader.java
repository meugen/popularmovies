package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.PagedReviewsDto;

/**
 * @author meugen
 */

public class MovieReviewsLoader extends AbstractLoader<PagedReviewsDto> {

    private static final String PARAM_ID = "id";

    public static Bundle buildParams(final int id) {
        final Bundle params = new Bundle();
        params.putInt(PARAM_ID, id);
        return params;
    }

    private int id;

    @Inject
    public MovieReviewsLoader(final Context context, final Api api) {
        super(context, api);
    }

    public void attachParams(final Bundle params) {
        this.id = params.getInt(PARAM_ID);
    }

    @Override
    protected PagedReviewsDto loadInBackground(final Api api) throws IOException {
        return api.movieReviews(this.id);
    }
}
