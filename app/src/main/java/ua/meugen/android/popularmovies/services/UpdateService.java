package ua.meugen.android.popularmovies.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.providers.MoviesContract;

public class UpdateService extends IntentService implements MoviesContract {

    private static final String TAG = UpdateService.class.getSimpleName();

    private static final String ACTION_POPULAR
            = "ua.meugen.android.popularmovies.action.POPULAR";
    private static final String ACTION_TOP_RATED
            = "ua.meugen.android.popularmovies.action.TOP_RATED";

    public static void startActionPopular(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_POPULAR);
        context.startService(intent);
    }

    public static void startActionTopRated(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_TOP_RATED);
        context.startService(intent);
    }

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                final Api api = PopularMovies.from(this).getApi();
                final String action = intent.getAction();
                if (ACTION_POPULAR.equals(action)) {
                    handleActionPopular(api);
                } else if (ACTION_TOP_RATED.equals(action)) {
                    handleActionTopRated(api);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    private void handleActionPopular(final Api api) throws IOException {
        final PagedMoviesDto movies = api.popularMovies();
        update(POPULAR_URI, movies.getResults());
    }

    private void handleActionTopRated(final Api api) throws IOException {
        final PagedMoviesDto movies = api.topRatedMovies();
        update(TOP_RATED_URI, movies.getResults());
    }

    private void update(final Uri uri, final List<MovieItemDto> movies) {
        final int count = movies.size();
        final ContentValues[] items = new ContentValues[count];

        int index = 0;
        for (MovieItemDto movie : movies) {
            final ContentValues item = new ContentValues();
            item.put(FIELD_ID, movie.getId());
            item.put(FIELD_POSTER_PATH, movie.getPosterPath());
            item.put(FIELD_ADULT, movie.isAdult());
            item.put(FIELD_OVERVIEW, movie.getOverview());
            item.put(FIELD_RELEASE_DATE, movie.getReleaseDate().getTime());
            item.put(FIELD_ORIGINAL_TITLE, movie.getOriginalTitle());
            item.put(FIELD_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
            item.put(FIELD_TITLE, movie.getTitle());
            item.put(FIELD_BACKDROP_PATH, movie.getBackdropPath());
            item.put(FIELD_POPULARITY, movie.getPopularity());
            item.put(FIELD_VOTE_COUNT, movie.getVoteCount());
            item.put(FIELD_VIDEO, movie.isVideo());
            item.put(FIELD_VOTE_AVERAGE, movie.getVoteAverage());
            items[index++] = item;
        }
        getContentResolver().bulkInsert(uri, items);
    }
}
