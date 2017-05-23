package ua.meugen.android.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.app.Api;
import ua.meugen.android.popularmovies.utils.ConnectivityUtils;


public abstract class AbstractLoader<T> extends AsyncTaskLoader<LoaderResult<T>> {

    private final Api api;

    private LoaderResult<T> result;

    protected AbstractLoader(final Context context, final Api api) {
        super(context);
        this.api = api;
    }

    @Override
    public final LoaderResult<T> loadInBackground() {
        LoaderResult<T> result;
        try {
            if (!ConnectivityUtils.isConnected(getContext())) {
                throw new NoNetworkException();
            }
            result = LoaderResult.withData(loadInBackground(api));
        } catch (IOException ex) {
            result = LoaderResult.withError(ex);
        }
        return result;
    }

    @Override
    protected final void onStartLoading() {
        if (this.result == null) {
            this.forceLoad();
        } else {
            this.deliverResult(this.result);
        }
    }

    @Override
    public void deliverResult(final LoaderResult<T> data) {
        super.deliverResult(data);
        this.result = data;
    }

    protected abstract T loadInBackground(final Api api) throws IOException;

    public static class NoNetworkException extends IOException {

        public NoNetworkException() {
            super("No connected network found");
        }
    }
}
