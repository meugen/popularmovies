package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.IOException;
import java.util.ArrayList;

import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.dto.VideosDto;

public class MovieDetailsLoader extends AbstractLoader<Bundle> {

    public static final String RESULT_VIDEOS = "videos";
    public static final String RESULT_REVIEWS = "reviews";

    private static final String PARAM_ID = "id";
    private static final String PARAM_WHAT = "what";

    private static final int WHAT_VIDEOS = 1;
    private static final int WHAT_REVIEWS = 2;

    private final int id;
    private final int what;

    public MovieDetailsLoader(final Context context, final Bundle params) {
        super(context);
        this.id = params.getInt(PARAM_ID);
        this.what = params.getInt(PARAM_WHAT);
    }

    @Override
    protected Bundle loadInBackground(final Api api) throws IOException {
        final Bundle bundle = new Bundle();
        if ((this.what & WHAT_VIDEOS) != 0) {
            final VideosDto dto = api.movieVideos(this.id);
            bundle.putParcelableArrayList(RESULT_VIDEOS, new ArrayList<>(dto.getResults()));
        }
        if ((this.what & WHAT_REVIEWS) != 0) {
            final PagedReviewsDto dto = api.movieReviews(this.id);
            bundle.putParcelableArrayList(RESULT_REVIEWS, new ArrayList<>(dto.getResults()));
        }
        return bundle;
    }

    public static class ParamsBuilder {

        private int id;
        private int what = 0;

        public ParamsBuilder movieId(final int value) {
            this.id = value;
            return this;
        }

        public ParamsBuilder includeVideos() {
            this.what |= WHAT_VIDEOS;
            return this;
        }

        public ParamsBuilder includeReviews() {
            this.what |= WHAT_REVIEWS;
            return this;
        }

        public Bundle build() {
            final Bundle bundle = new Bundle();
            bundle.putInt(PARAM_ID, this.id);
            bundle.putInt(PARAM_WHAT, this.what);
            return bundle;
        }
    }
}
