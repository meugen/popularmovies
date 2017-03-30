package ua.meugen.android.popularmovies.loaders;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.IOException;


public abstract class AbstractCallbacks<T> implements LoaderManager.LoaderCallbacks<LoaderResult<T>> {

    private final Handler handler;

    protected AbstractCallbacks() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public final void onLoadFinished(
            final Loader<LoaderResult<T>> loader,
            final LoaderResult<T> data) {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                _onLoadFinished(data);
            }
        });
    }

    private void _onLoadFinished(final LoaderResult<T> data) {
        try {
            onData(data.getData());
        } catch (AbstractLoader.NoNetworkException ex) {
            onNoNetwork();
        } catch (IOException ex) {
            onError(ex);
        } finally {
            onCompleted();
        }
    }

    @Override
    public void onLoaderReset(final Loader<LoaderResult<T>> loader) {}

    protected abstract void onData(final T data);

    protected abstract void onError(final IOException ex);

    protected abstract void onNoNetwork();

    protected void onCompleted() {}
}
