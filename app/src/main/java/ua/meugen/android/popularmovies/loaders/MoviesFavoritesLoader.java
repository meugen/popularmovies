package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.providers.MoviesContract;

/**
 * @author meugen
 */

public class MoviesFavoritesLoader extends AsyncTaskLoader<Void> implements MoviesContract {

    private static final String PARAM_ID = "id";
    private static final String PARAM_INSERT = "insert";

    public static Bundle buildParams(final int id, final boolean insert) {
        final Bundle params = new Bundle();
        params.putInt(PARAM_ID, id);
        params.putBoolean(PARAM_INSERT, insert);
        return params;
    }

    private int id;
    private boolean insert;

    @Inject
    public MoviesFavoritesLoader(final Context context) {
        super(context);
    }

    public void attachParams(final Bundle params) {
        this.id = params.getInt(PARAM_ID);
        this.insert = params.getBoolean(PARAM_INSERT);
    }

    @Override
    public Void loadInBackground() {
        final Uri uri = MOVIES_URI.buildUpon()
                .appendPath(Integer.toString(id))
                .appendPath(FAVORITES)
                .build();
        if (insert) {
            getContext().getContentResolver().insert(uri, null);
        } else {
            getContext().getContentResolver().delete(uri, null, null);
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
