package ua.meugen.android.popularmovies.loaders;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.IOException;


public abstract class AbstractCallbacks<T> implements LoaderManager.LoaderCallbacks<LoaderResult<T>> {

    @Override
    public final void onLoadFinished(
            final Loader<LoaderResult<T>> loader,
            final LoaderResult<T> data) {
        try {
            onData(data.getData());
        } catch (AbstractLoader.NoNetworkException ex) {
            onNoNetwork();
        } catch (IOException ex) {
            onError(ex);
        }
    }

    @Override
    public void onLoaderReset(final Loader<LoaderResult<T>> loader) {}

    protected abstract void onData(final T data);

    protected abstract void onError(final IOException ex);

    protected abstract void onNoNetwork();
}
